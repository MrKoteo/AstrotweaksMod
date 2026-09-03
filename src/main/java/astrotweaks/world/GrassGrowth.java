package astrotweaks.world;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockDoublePlant.EnumPlantType;
import net.minecraft.block.BlockDoublePlant.EnumBlockHalf;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import java.util.concurrent.ThreadLocalRandom;

import astrotweaks.ModVariables;

@Mod.EventBusSubscriber(modid = "astrotweaks")
public class GrassGrowth {
	// Configuration: delay range in seconds
	/*
	private static int GRASS_DENSITY = ModVariables.GG_Density;
    private static int MIN_DELAY_TICKS = ModVariables.GG_MIN_DELAY_TICK;
    private static int MAX_DELAY_TICKS = ModVariables.GG_MAX_DELAY_TICK;
    private static final BitSet BIOME_BLACKLIST = ModVariables.GGBlacklist;
    private static int MAX_OPER_PER_TICK = ModVariables.GG_MAX_OPER_PER_TICK;
    private static boolean GG_ON = ModVariables.GG_ENABLED;*/

	private static final int GRASS_THRESHOLD = 13;
	private static final int TALL_GRASS_THRESHOLD = 3;
	
    private static int GRASS_DENSITY;
	private static int TALL_GRASS_DENSITY;
    private static int MIN_DELAY_TICKS;
    private static int MAX_DELAY_TICKS;
    private static int MAX_OPER_PER_TICK;
    private static boolean GG_ON;
    //private static BitSet BIOME_BLACKLIST;

    public static void reloadFromConfig() {
        GRASS_DENSITY = ModVariables.GG_Density;
		TALL_GRASS_DENSITY = ModVariables.GG_Tall_Density;
        MIN_DELAY_TICKS = ModVariables.GG_MIN_DELAY_TICK;
        MAX_DELAY_TICKS = ModVariables.GG_MAX_DELAY_TICK;
        MAX_OPER_PER_TICK = ModVariables.GG_MAX_OPER_PER_TICK;
        GG_ON = ModVariables.GG_ENABLED;
    }

    // Per-dimension priority queues
	private static final Map<Integer, PriorityQueue<ScheduledChunk>> queues = new ConcurrentHashMap<>();
	private static final Map<Integer, Set<Long>> loadedChunks = new ConcurrentHashMap<>();
	private static final Map<Integer, Map<Long, Long>> scheduledTimes = new ConcurrentHashMap<>();

    static {
        if (MIN_DELAY_TICKS > MAX_DELAY_TICKS) {
            int temp = MIN_DELAY_TICKS;
            MIN_DELAY_TICKS = MAX_DELAY_TICKS;
            MAX_DELAY_TICKS = temp;
        }
        //if (MIN_DELAY_TICKS == MAX_DELAY_TICKS)
        //    MAX_DELAY_TICKS = MIN_DELAY_TICKS + 1;
        
		if (MIN_DELAY_TICKS < 1) MIN_DELAY_TICKS = 1;
		if (MAX_DELAY_TICKS < 1) MAX_DELAY_TICKS = 1;
    }

    // Helper class for the priority queue
    private static class ScheduledChunk implements Comparable<ScheduledChunk> {
        final long chunkKey;
        final int dimension;
        final long scheduledTime;

        ScheduledChunk(long chunkKey, int dimension, long scheduledTime) {
            this.chunkKey = chunkKey;
            this.dimension = dimension;
            this.scheduledTime = scheduledTime;
        }
        @Override
        public int compareTo(ScheduledChunk o) {
            return Long.compare(this.scheduledTime, o.scheduledTime);
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ScheduledChunk)) return false;
            ScheduledChunk that = (ScheduledChunk) o;
            return chunkKey == that.chunkKey && dimension == that.dimension;
        }
        @Override
        public int hashCode() {
            return Objects.hash(chunkKey, dimension);
        }
    }

    // Helper methods to get per-dimension structures
	private static PriorityQueue<ScheduledChunk> getQueue(int dim) {
	    return queues.computeIfAbsent(dim, k -> new PriorityQueue<>());
	}
	
	private static Set<Long> getLoadedSet(int dim) {
	    return loadedChunks.computeIfAbsent(dim, k -> ConcurrentHashMap.newKeySet());
	}
	
	private static Map<Long, Long> getScheduledMap(int dim) {
	    return scheduledTimes.computeIfAbsent(dim, k -> new ConcurrentHashMap<>());
	}

	private static void addToQueue(int dim, ScheduledChunk chunk) {
	    if (chunk == null) return;
	    PriorityQueue<ScheduledChunk> q = getQueue(dim);
	    synchronized (q) {
	        q.add(chunk);
	    }
	}
    // Remove all nulls from the queue (call before processing)
    //private static void cleanQueue(int dim) {
    //    PriorityQueue<ScheduledChunk> queue = getQueue(dim);
    //    queue.removeIf(Objects::isNull);
    //}

    // -------- Chunk load/unload events --------
    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
    	if (!GG_ON) return;
        World world = event.getWorld();
        if (world == null || world.provider.getDimension() != 0) return;
        Chunk chunk = event.getChunk();
        long key = ChunkPos.asLong(chunk.x, chunk.z);
        int dim = world.provider.getDimension();

        getLoadedSet(dim).add(key);

        // Schedule first check with random delay
        long currentTick = world.getTotalWorldTime();
        long delay = MIN_DELAY_TICKS + ThreadLocalRandom.current().nextInt(MAX_DELAY_TICKS - MIN_DELAY_TICKS + 1);
        long scheduled = currentTick + delay;

        Map<Long, Long> times = getScheduledMap(dim);
        times.put(key, scheduled);
        addToQueue(dim, new ScheduledChunk(key, dim, scheduled));
    }

    @SubscribeEvent
    public static void onChunkUnload(ChunkEvent.Unload event) {
    	if (!GG_ON) return;
        World world = event.getWorld();
        if (world == null || world.provider.getDimension() != 0) return;
        Chunk chunk = event.getChunk();
        long key = ChunkPos.asLong(chunk.x, chunk.z);
        int dim = world.provider.getDimension();

        getLoadedSet(dim).remove(key);
        getScheduledMap(dim).remove(key);
        // The queue entry will be ignored during processing if not found in loaded set
    }

    // -------- World tick processing --------
    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
    	if (!GG_ON) return;
        if (event.phase != TickEvent.Phase.END) return;
        World world = event.world;
        //if (world == null) return;
        if (world.isRemote) return; // server only
        int dim = world.provider.getDimension();
        if (dim != 0) return;

        
        PriorityQueue<ScheduledChunk> queue = getQueue(dim);
		//cleanQueue(dim);

        Set<Long> loaded = getLoadedSet(dim);
        Map<Long, Long> times = getScheduledMap(dim);
        long currentTick = world.getTotalWorldTime();

        int processed = 0;
        while (processed < MAX_OPER_PER_TICK) {
		    ScheduledChunk scheduled;
		    synchronized (queue) {
		        if (queue.isEmpty() || processed >= MAX_OPER_PER_TICK) break;
		        scheduled = queue.peek();
		        if (scheduled == null) { queue.poll(); continue; }
		        if (scheduled.scheduledTime > currentTick) break;
		        queue.poll(); // remove from queue
		    }

			//ScheduledChunk scheduled = queue.peek();
			//if (scheduled == null) continue;
			//if (scheduled.scheduledTime > currentTick) break;
			//queue.poll(); // remove from queue

            long key = scheduled.chunkKey;
            Long actualTime = times.get(key);
            if (actualTime == null || actualTime != scheduled.scheduledTime) {
                // Stale entry (chunk reloaded) - skip
                continue;
            }
            if (!loaded.contains(key)) {
                // Chunk unloaded - don't reschedule
                continue;
            }

			int cx = (int)(key & 0xFFFFFFFFL);
			int cz = (int)((key >>> 32) & 0xFFFFFFFFL);
			//ChunkPos pos = new ChunkPos(cx, cz);
            Chunk chunk = world.getChunkFromChunkCoords(cx, cz);
			if (chunk == null || !chunk.isLoaded()) continue;
            // Perform growth logic
            //try {
                performGrowth(world, chunk);
            //} catch (Exception e) {
                //e.printStackTrace();
            //}

            // Reschedule for another random delay
		    long delay = MIN_DELAY_TICKS + ThreadLocalRandom.current().nextInt(MAX_DELAY_TICKS - MIN_DELAY_TICKS + 1);
		    long newScheduled = currentTick + delay;
		    times.put(key, newScheduled);
		    synchronized (queue) { queue.add(new ScheduledChunk(key, dim, newScheduled)); }
		    processed++;
        }
    }
    // -------- Main growth algorithm --------
    private static void performGrowth(World world, Chunk chunk) {
	    final int baseX = chunk.x * 16;
	    final int baseZ = chunk.z * 16;
	    ThreadLocalRandom rnd = ThreadLocalRandom.current();

	    int x = baseX + rnd.nextInt(16);
	    int z = baseZ + rnd.nextInt(16);

	    int[] ox = {4, 4, -4, -4};
	    int[] oz = {4, -4, 4, -4};
	    BlockPos.MutableBlockPos mcheck = new BlockPos.MutableBlockPos();
	    for (int i = 0; i < 4; i++) {
	        mcheck.setPos(x + ox[i], 64, z + oz[i]);
	        if (!world.isBlockLoaded(mcheck)) return;
	    }
        // 1. Check biome before any block scanning (must be allowed)
	    //BlockPos.MutableBlockPos mpos = new BlockPos.MutableBlockPos(x, 64, z);
	    //Biome biome = world.getBiome(mpos);
	    //if (isBiomeInBlacklist(biome)) return;

	    final int MID = 64;
	    final int MINY = 2;
	    final int MAXY = 180;
	    BlockPos.MutableBlockPos probe = new BlockPos.MutableBlockPos(x, 64, z);;
	    IBlockState state;
		Block block;
	    BlockPos grassPos = null;

		boolean encounteredSolidInUpScan = false;
	    for (int y = MID; y <= MAXY; y++) {
			// По сути мы должны заранее гарантировать, что проходим только по разрешённой области

		    probe.setY(y);
		    state = world.getBlockState(probe);
			block = state.getBlock();
		    if (block == Blocks.GRASS) {
		        grassPos = probe.toImmutable();
		        break;
		    }
		    if (block == Blocks.DIRT) {
		        BlockDirt.DirtType type = state.getValue(BlockDirt.VARIANT);
		        if (type == BlockDirt.DirtType.PODZOL) {
		            grassPos = probe.toImmutable();
		            break;
		        }
		    }
			// Обнаружен твердый объект при сканировании вверх
		    //IBlockState probeState = state;
		    if (block.isFullCube(state)) encounteredSolidInUpScan = true;
		    else if (state.getLightOpacity(world, probe) > 0) encounteredSolidInUpScan = true;
	    }

		if (grassPos == null && !encounteredSolidInUpScan) {
		    for (int y = MID - 1; y >= MINY; y--) {
		        probe.setY(y);
		        state = world.getBlockState(probe);
				block = state.getBlock();
		        if (block == Blocks.GRASS) {
		            grassPos = probe.toImmutable();
		            break;
		        }
		        if (block == Blocks.DIRT) {
		            BlockDirt.DirtType type = state.getValue(BlockDirt.VARIANT);
		            if (type == BlockDirt.DirtType.PODZOL) {
		                grassPos = probe.toImmutable();
		                break;
		            }
		        }
		        //IBlockState probeState = state;
		        if (block.isFullCube(state) || state.getLightOpacity(world, probe) > 0) {
		            break;
		        }
		    }
		}
	    if (grassPos == null) return; // это важно, так как grassPos бывает null 

		// ---- Подсчёт травы и высокой травы в окрестности 5x3x5 ----
		int tallCount = 0;
		int doubleCount = 0;
		BlockPos.MutableBlockPos check = new BlockPos.MutableBlockPos();
		for (int dx = -2; dx <= 2; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				for (int dz = -2; dz <= 2; dz++) {
					check.setPos(grassPos.getX() + dx, grassPos.getY() + dy, grassPos.getZ() + dz);
					state = world.getBlockState(check);
					block = state.getBlock();
					if (block == Blocks.TALLGRASS) {
						tallCount++;
					}
					if (block == Blocks.DOUBLE_PLANT) {
						// Проверяем, что это высокая трава (variant=GRASS) и нижняя часть
						if (state.getValue(BlockDoublePlant.VARIANT) == EnumPlantType.GRASS && state.getValue(BlockDoublePlant.HALF) == EnumBlockHalf.LOWER) {
							doubleCount++;
						}
					}
				}
			}
		}
		if (tallCount == 0 && doubleCount == 0) return;
	    if (tallCount + doubleCount > 24) return;


		// ---- Попытка поставить высокую траву ----
		if (((tallCount >= GRASS_THRESHOLD  && doubleCount >= TALL_GRASS_THRESHOLD) || tallCount == GRASS_DENSITY) && doubleCount < TALL_GRASS_DENSITY) {
			BlockPos above1 = grassPos.up();
			BlockPos above2 = grassPos.up(2);
			// Проверяем, что два блока сверху воздух и не выходят за пределы мира
			if ((world.isAirBlock(above1) || world.getBlockState(above1).getBlock() == Blocks.TALLGRASS) && world.isAirBlock(above2)) {
				IBlockState lowerState = Blocks.DOUBLE_PLANT.getDefaultState()
						.withProperty(BlockDoublePlant.VARIANT, EnumPlantType.GRASS)
						.withProperty(BlockDoublePlant.HALF, EnumBlockHalf.LOWER);
				IBlockState upperState = Blocks.DOUBLE_PLANT.getDefaultState()
						.withProperty(BlockDoublePlant.VARIANT, EnumPlantType.GRASS)
						.withProperty(BlockDoublePlant.HALF, EnumBlockHalf.UPPER);
				world.setBlockState(above1, lowerState, 2);
				world.setBlockState(above2, upperState, 2);
				return; // успешно поставили - выходим
			}
		}

		// ---- Иначе ставим обычную траву ----
		if (/*(tallCount > 0 || doubleCount > 0) &&*/ tallCount <= GRASS_DENSITY) {
			BlockPos above = grassPos.up();
			if (world.isAirBlock(above)) {
				world.setBlockState(above, Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.GRASS), 2);
				return;
			}
		}
	}

    // -------- Biome whitelist check (uses your ModVariables.GGAllowed) --------
	//private static boolean isBiomeInBlacklist(Biome biome) {
	//    int id = Biome.REGISTRY.getIDForObject(biome);
	//    return id >= 0 && BIOME_BLACKLIST.get(id);
	//}
    // -------- Cleanup on world load (avoid stale data across sessions) --------
    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event) {
    	if (!GG_ON) return;
        World world = event.getWorld();
        if (world == null || world.provider.getDimension() != 0) return;
        int dim = world.provider.getDimension();
        queues.remove(dim);
        loadedChunks.remove(dim);
        scheduledTimes.remove(dim);
    }
}
