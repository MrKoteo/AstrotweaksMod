package astrotweaks.world;

import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Random;

import astrotweaks.block.BlockMineralsOre;
import astrotweaks.block.BlockQuartzOreGranite;
import astrotweaks.block.BlockQuartzOreStone;
import astrotweaks.block.BlockRubyOre;

import astrotweaks.ModVariables;

public class BlockWorldGen {

    public static void register(){
        if (ModVariables.OW_Minerals_Gen) {
            GameRegistry.registerWorldGenerator(new IWorldGenerator() {
                @Override public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator cg, IChunkProvider cp) {
                    int dimID = world.provider.getDimension();
                    BlockMineralsOre.generateWorld(random, chunkX * 16, chunkZ * 16, world, dimID, cg, cp);
                }
            }, 5);
        }
        if (ModVariables.OW_Quartz_Gen) {
            GameRegistry.registerWorldGenerator(new IWorldGenerator() {
                @Override public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator cg, IChunkProvider cp) {
                    int dimID = world.provider.getDimension();
                    BlockQuartzOreStone.generateWorld(random, chunkX * 16, chunkZ * 16, world, dimID, cg, cp);
                    BlockQuartzOreGranite.generateWorld(random, chunkX * 16, chunkZ * 16, world, dimID, cg, cp);
                }
            }, 5);
        }
        if (ModVariables.OW_Ruby_Gen) {
            GameRegistry.registerWorldGenerator(new IWorldGenerator() {
                @Override public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator cg, IChunkProvider cp) {
                    int dimID = world.provider.getDimension();
                    BlockRubyOre.generateWorld(random, chunkX * 16, chunkZ * 16, world, dimID, cg, cp);
                }
            }, 5);
        }
    }
}



/*
        GameRegistry.registerWorldGenerator(new IWorldGenerator() {
            @Override
            public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator cg, IChunkProvider cp) {
                int dimID = world.provider.getDimension();
                BlockRubyOre.generateWorld(random, chunkX * 16, chunkZ * 16, world, dimID, cg, cp);
                BlockMineralsOre.generateWorld(random, chunkX * 16, chunkZ * 16, world, dimID, cg, cp);
                BlockQuartzOreStone.generateWorld(random, chunkX * 16, chunkZ * 16, world, dimID, cg, cp);
                BlockQuartzOreGranite.generateWorld(random, chunkX * 16, chunkZ * 16, world, dimID, cg, cp);
            }
        }, 5);

*/