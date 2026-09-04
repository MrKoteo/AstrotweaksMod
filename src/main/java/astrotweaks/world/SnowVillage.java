package astrotweaks.world;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Biomes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.ArrayList;
import java.util.List;

// подписка не нужна
public final class SnowVillage {
    public static final ArrayList<Biome> coldBiomes = new ArrayList<>();

    public SnowVillage() {}

    public static void preInit() {
		coldBiomes.add(Biomes.ICE_PLAINS);
		coldBiomes.add(Biomes.COLD_BEACH);
		coldBiomes.add(Biomes.COLD_TAIGA);

		List<Biome> villageBiomes = new ArrayList<>(MapGenVillage.VILLAGE_SPAWN_BIOMES);
		villageBiomes.addAll(coldBiomes);
		MapGenVillage.VILLAGE_SPAWN_BIOMES = villageBiomes;

		MinecraftForge.TERRAIN_GEN_BUS.register(new VillageMaterialHandler());
    }
    public static class VillageMaterialHandler {
        @SubscribeEvent
        public void getVillageBlockID(BiomeEvent.GetVillageBlockID event) {
            if (SnowVillage.coldBiomes.contains(event.getBiome())) {
                IBlockState original = event.getOriginal();
                Block block = original.getBlock();
                IBlockState replacement = null;
                if (block == Blocks.PLANKS)
                    replacement = Blocks.PLANKS.getDefaultState()
                        .withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.SPRUCE);
                if (block == Blocks.LOG)
                    replacement = Blocks.LOG.getDefaultState()
                        .withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE)
                        .withProperty(BlockLog.LOG_AXIS, original.getValue(BlockLog.LOG_AXIS));
                if (block == Blocks.OAK_STAIRS) 
                    replacement = Blocks.SPRUCE_STAIRS.getDefaultState()
                        .withProperty(BlockStairs.FACING, original.getValue(BlockStairs.FACING))
                        .withProperty(BlockStairs.HALF, original.getValue(BlockStairs.HALF))
                        .withProperty(BlockStairs.SHAPE, original.getValue(BlockStairs.SHAPE));
                if (block == Blocks.OAK_DOOR)
                    replacement = Blocks.SPRUCE_DOOR.getDefaultState();
				if (block == Blocks.OAK_FENCE)
				    replacement = Blocks.SPRUCE_FENCE.getDefaultState();

                if (replacement != null) {
                    event.setReplacement(replacement);
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }
}
