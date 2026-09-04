package astrotweaks.world;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import astrotweaks.block.BlockBush1;
import astrotweaks.block.BlockBush2;
import astrotweaks.block.BlockBush3;
import astrotweaks.block.BlockBush4;
import astrotweaks.block.BlockBush5;
import astrotweaks.block.BlockBush6;
import astrotweaks.block.BlockBush7;
import astrotweaks.block.BlockFern1;



public class BushDecorator {
	private static List<BushEntry> BUSHES = new ArrayList<>();
	private static Map<Biome, List<BushEntry>> BUSH_MAP;

	private static Set<Block> GROUND_BLOCKS;
	private static Set<Block> REPLACEABLE_BLOCKS;

	private static final Set<ResourceLocation> Bush1_biomes = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
		new ResourceLocation("forest"),new ResourceLocation("taiga"),new ResourceLocation("forest_hills"), new ResourceLocation("taiga_hills"),
		new ResourceLocation("birch_forest"),new ResourceLocation("birch_forest_hills"),new ResourceLocation("roofed_forest"), new ResourceLocation("redwood_taiga"),
		new ResourceLocation("redwood_taiga_hills"),new ResourceLocation("smaller_extreme_hills"),new ResourceLocation("extreme_hills_with_trees"),
		new ResourceLocation("mutated_forest"),new ResourceLocation("mutated_taiga"),new ResourceLocation("mutated_birch_forest"), 
		new ResourceLocation("mutated_birch_forest_hills"),new ResourceLocation("mutated_roofed_forest"),new ResourceLocation("mutated_redwood_taiga"),
		new ResourceLocation("mutated_redwood_taiga_hills"),new ResourceLocation("mutated_extreme_hills_with_trees"),new ResourceLocation("extreme_hills")
	))); // Bush
	private static final Set<ResourceLocation> Bush2_biomes = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
		new ResourceLocation("swampland"), new ResourceLocation("redwood_taiga"), new ResourceLocation("redwood_taiga_hills"),
		new ResourceLocation("mutated_swampland"), new ResourceLocation("mutated_redwood_taiga"),new ResourceLocation("mutated_redwood_taiga_hills")
	))); // Swamp bush
	private static final Set<ResourceLocation> Bush3_biomes = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
		new ResourceLocation("taiga"),new ResourceLocation("taiga_hills"),new ResourceLocation("redwood_taiga"),new ResourceLocation("redwood_taiga_hills"),
		new ResourceLocation("smaller_extreme_hills"), new ResourceLocation("extreme_hills_with_trees"),new ResourceLocation("mutated_taiga"),
		new ResourceLocation("mutated_redwood_taiga"),new ResourceLocation("mutated_redwood_taiga_hills"),new ResourceLocation("mutated_extreme_hills_with_trees"),
		new ResourceLocation("taiga_cold"),new ResourceLocation("taiga_cold_hills"),new ResourceLocation("mutated_taiga_cold")
	))); // Taiga
	private static final Set<ResourceLocation> Bush4_biomes = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
		new ResourceLocation("jungle"),new ResourceLocation("jungle_hills"),new ResourceLocation("jungle_edge"),new ResourceLocation("mutated_jungle"),
		new ResourceLocation("mutated_jungle_edge")
	))); // Jungle
	private static final Set<ResourceLocation> Bush5_biomes = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
		new ResourceLocation("forest"),new ResourceLocation("forest_hills"),new ResourceLocation("mutated_forest"),new ResourceLocation("birch_forest_hills"),
		new ResourceLocation("birch_forest"),new ResourceLocation("roofed_forest"),new ResourceLocation("smaller_extreme_hills"),
		new ResourceLocation("extreme_hills_with_trees"),new ResourceLocation("mutated_birch_forest"),new ResourceLocation("mutated_birch_forest_hills"), 
		new ResourceLocation("mutated_roofed_forest"),new ResourceLocation("mutated_extreme_hills_with_trees")
	))); // Siren' ?
	private static final Set<ResourceLocation> Bush6_biomes = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
		new ResourceLocation("plains"), new ResourceLocation("mutated_plains"), new ResourceLocation("birch_forest"), new ResourceLocation("birch_forest_hills"),
		new ResourceLocation("mutated_birch_forest"),new ResourceLocation("mutated_birch_forest_hills")
	))); // Smooth bush
	private static final Set<ResourceLocation> Bush7_biomes = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
		new ResourceLocation("jungle"),new ResourceLocation("jungle_hills"),new ResourceLocation("jungle_edge"),new ResourceLocation("mutated_jungle"), 
		new ResourceLocation("mutated_jungle_edge")
	))); // Jungle
	private static final Set<ResourceLocation> Fern1_biomes = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
		new ResourceLocation("taiga"), new ResourceLocation("taiga_hills"), new ResourceLocation("mutated_taiga"),new ResourceLocation("redwood_taiga"),
		new ResourceLocation("redwood_taiga_hills"),new ResourceLocation("mutated_redwood_taiga"), new ResourceLocation("mutated_redwood_taiga_hills")
	))); // Fern

    private static Set<Biome> Bush1_biomes_ch;
    private static Set<Biome> Bush2_biomes_ch;
    private static Set<Biome> Bush3_biomes_ch;
    private static Set<Biome> Bush4_biomes_ch;
    private static Set<Biome> Bush5_biomes_ch;
    private static Set<Biome> Bush6_biomes_ch;
    private static Set<Biome> Bush7_biomes_ch;
    private static Set<Biome> Fern1_biomes_ch;

    private static Set<Biome> toBiomeSet(Set<ResourceLocation> rlSet) {
        Set<Biome> biomeSet = new HashSet<>();
        for (ResourceLocation rl : rlSet) {
            Biome biome = Biome.REGISTRY.getObject(rl);
            if (biome != null) {
                biomeSet.add(biome);
            } else {
                System.err.println("Biome not found: " + rl);
            }
        }
        return Collections.unmodifiableSet(biomeSet);
    }
	///
	public static void addBush(Block block, Set<Biome> biomes, double frequency, int clusterDensity) {
		BUSHES.add(new BushEntry(block.getDefaultState(), biomes, frequency, clusterDensity));
	}
	public static void init() {
		System.out.println("Bush Decorator was Init !");
        Bush1_biomes_ch = toBiomeSet(Bush1_biomes);
        Bush2_biomes_ch = toBiomeSet(Bush2_biomes);
        Bush3_biomes_ch = toBiomeSet(Bush3_biomes);
        Bush4_biomes_ch = toBiomeSet(Bush4_biomes);
        Bush5_biomes_ch = toBiomeSet(Bush5_biomes);
        Bush6_biomes_ch = toBiomeSet(Bush6_biomes);
        Bush7_biomes_ch = toBiomeSet(Bush7_biomes);
        Fern1_biomes_ch = toBiomeSet(Fern1_biomes);

		//		Block, 			  Biome, 		chance, count
		addBush(BlockBush1.block, Bush1_biomes_ch, 1.5, 8); // Forest
		addBush(BlockBush2.block, Bush2_biomes_ch, 1.9, 9); // swamp
		addBush(BlockBush3.block, Bush3_biomes_ch, 1.7, 7); // Taiga
		addBush(BlockBush4.block, Bush4_biomes_ch, 1.8, 11);// Jungle
		addBush(BlockBush5.block, Bush5_biomes_ch, 1.0, 6); // siren
		addBush(BlockBush6.block, Bush6_biomes_ch, 0.65,8); // Plains
		addBush(BlockBush7.block, Bush7_biomes_ch, 1.6, 9); // Jungle

		addBush(BlockFern1.block, Fern1_biomes_ch, 2.3, 12); //

		// final
		BUSHES = Collections.unmodifiableList(new ArrayList<>(BUSHES));

	    Map<Biome, List<BushEntry>> map = new HashMap<>();
	    for (BushEntry e : BUSHES) {
	        for (Biome b : e.biomes) {
	            map.computeIfAbsent(b, k -> new ArrayList<>()).add(e);
	        }
	    }
	    // make immutable
	    Map<Biome, List<BushEntry>> immutable = new HashMap<>();
	    for (Map.Entry<Biome,List<BushEntry>> me : map.entrySet()) {
	        immutable.put(me.getKey(), Collections.unmodifiableList(me.getValue()));
	    }
	    BUSH_MAP = Collections.unmodifiableMap(immutable);

	    initBlockSets();
	}

	///
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onDecorate(DecorateBiomeEvent.Decorate event) {
        if (event.getType() != DecorateBiomeEvent.Decorate.EventType.FLOWERS) return;

        World world = event.getWorld();
        Random rand = event.getRand();
        BlockPos chunkPos = event.getPos(); // getPos важно, иначе координаты чанка получаются неправильно и происходит NPE краш
        //if (chunkPos == null) { return; } // Это всегда будет Null, если получать chunkPos через getPlacementPos()
        Biome currentBiome = world.getBiome(chunkPos);

        List<BushEntry> entries = BUSH_MAP.get(currentBiome);
        if (entries == null) return;

        for (BushEntry entry : entries) {
            int attempts = getAttempts(entry.frequency, rand);
            for (int i = 0; i < attempts; i++) {
                generateBushCluster(world, rand, chunkPos, entry);
            }
        }
    }
	
	private static void initBlockSets() {
        Set<Block> ground = new HashSet<>();
        Set<Block> replaceable = new HashSet<>();
		// def
        ground.add(Blocks.GRASS);
        ground.add(Blocks.DIRT);

        replaceable.add(Blocks.AIR);
        replaceable.add(Blocks.TALLGRASS);
        replaceable.add(Blocks.RED_FLOWER);
        replaceable.add(Blocks.SNOW_LAYER);
	
	    // Add blocks for "templates" registryName,
	    for (Block b : Block.REGISTRY) {
	        ResourceLocation rl = b.getRegistryName();
	        if (rl == null) continue;
	        String ns = rl.getResourceDomain(); // getNamespace() return domain
	        String path = rl.getResourcePath();

	        // if namespace "ore" & contain "grass" -> ground
            if ("ore".equals(ns) && path.contains("grass") && !path.contains("tall")) {
                ground.add(b);
            }
	        //if ("ore".equals(ns) && path.contains("dirt")) {
	        //    ground.add(b);
	        //}
            if ("ore".equals(ns) && path.contains("tallgrass")) {
                replaceable.add(b);
            }
	    }
        GROUND_BLOCKS = Collections.unmodifiableSet(ground);
        REPLACEABLE_BLOCKS = Collections.unmodifiableSet(replaceable);

        System.out.println("BushDecorator: ground set size=" + GROUND_BLOCKS.size() + " replaceable set size=" + REPLACEABLE_BLOCKS.size());
	}

    private static void generateBushCluster(World world, Random rand, BlockPos chunkPos, BushEntry entry) {
        int chunkX = chunkPos.getX() >> 4;
        int chunkZ = chunkPos.getZ() >> 4;
        int originX = chunkX << 4;
        int originZ = chunkZ << 4;

	    //Chunk chunk = world.getChunk(chunkX, chunkZ);
        Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
        if (chunk == null) return;
	
	    // calc center; exclude [0..15] borders by using [1..14]
	    int localX = rand.nextInt(14) + 1; // [1..14]
	    int localZ = rand.nextInt(14) + 1;
	    int x = originX + localX;
	    int z = originZ + localZ;
	
        // OPT: Reusable MutableBlockPos for height and placement lookup
        BlockPos.MutableBlockPos mpos = new BlockPos.MutableBlockPos();
        int y = getGroundHeight(chunk, localX, localZ, mpos);
        if (y <= 0 || y < 59 || y > 210) return;
	
        mpos.setPos(x, y, z);
        IBlockState state = entry.state; // OPT: local var
        if (!canPlaceBush(chunk, x, y, z, state)) return;
        chunk.setBlockState(mpos, state);

	    // place additional bushes around (density)
	    int density = entry.clusterDensity;
        for (int i = 1; i < density; i++) {
            int dx = rand.nextInt(7) - 3;
            int dz = rand.nextInt(7) - 3;
            int dy = rand.nextInt(5) - 2;

            int cx = x + dx;
            int cz = z + dz;

            // checking for belonging to the same chunk
            if ((cx >> 4) != chunkX || (cz >> 4) != chunkZ) continue;

            int localCx = cx & 15, localCz = cz & 15;
            if (localCx == 0 || localCx == 15 || localCz == 0 || localCz == 15) continue;

            int cy = y + dy;
            // OPT: reuse mpos
            mpos.setPos(cx, cy, cz);
            if (canPlaceBush(chunk, cx, cy, cz, state)) {
                chunk.setBlockState(mpos, state);
            }
        }
	}

	// Finds the height (y) of the top block of grass or earth in the given column,
	// above which there is air (as for flowers).
	// OPT: Use getHeightValue to speed up height lookups
	// OPT: Pass a reusable MutableBlockPos
    private static int getGroundHeight(Chunk chunk, int localX, int localZ, BlockPos.MutableBlockPos mpos) {
        int baseX = (chunk.x << 4) + localX;
        int baseZ = (chunk.z << 4) + localZ;
        int topY = chunk.getHeightValue(localX, localZ);
        int startY = Math.min(211, topY + 5);

        for (int y = startY; y > 0; y--) {
            mpos.setPos(baseX, y, baseZ);
            IBlockState state = chunk.getBlockState(mpos);
            Block block = state.getBlock();

            mpos.setPos(baseX, y + 1, baseZ);
            IBlockState above = chunk.getBlockState(mpos);
            Block aboveBlock = above.getBlock();

            if (GROUND_BLOCKS.contains(block) && REPLACEABLE_BLOCKS.contains(aboveBlock)) {
                return y + 1;
            }
        }
        return -1;
    }
    private static int getAttempts(double frequency, Random rand) {
        if (frequency >= 1.0) {
            int base = (int) frequency;
            double remainder = frequency - base;
            return (rand.nextDouble() < remainder) ? base + 1 : base;
        } else {
            return (rand.nextDouble() < frequency) ? 1 : 0;
        }
    }

	// OPT: New signature - accept int coordinates instead of BlockPos
	// OPT: Removed chunk boundary checking (it's already done in the calling code)
    private static boolean canPlaceBush(Chunk chunk, int x, int y, int z, IBlockState bushState) {
        // check that the position is replaceable
        IBlockState stateAtPos = chunk.getBlockState(x, y, z);
        if (!REPLACEABLE_BLOCKS.contains(stateAtPos.getBlock())) return false;
        // check down
        IBlockState below = chunk.getBlockState(x, y - 1, z);
        return GROUND_BLOCKS.contains(below.getBlock());
    }

    private static class BushEntry {
        final IBlockState state;
        final Set<Biome> biomes;
        final double frequency;
        final int clusterDensity;
        BushEntry(IBlockState state, Set<Biome> biomes, double frequency, int clusterDensity) {
            this.state = state;
            this.biomes = biomes;
            this.frequency = frequency;
            this.clusterDensity = clusterDensity;
        }
    }
}
