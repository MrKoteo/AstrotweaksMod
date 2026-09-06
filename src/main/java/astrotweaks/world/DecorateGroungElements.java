package astrotweaks.world;

import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Random;

import astrotweaks.block.BlockGroundRock1;
import astrotweaks.block.BlockGroundRock2;
import astrotweaks.block.BlockGroundStick;


public class DecorateGroungElements {

    public static void register() { // Контролируется в мейне
        GameRegistry.registerWorldGenerator(new IWorldGenerator() {
            @Override
            public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator cg, IChunkProvider cp) {
                int dimID = world.provider.getDimension();
                BlockGroundRock1.generateWorld(random, chunkX * 16, chunkZ * 16, world, dimID, cg, cp);
                BlockGroundRock2.generateWorld(random, chunkX * 16, chunkZ * 16, world, dimID, cg, cp);
                BlockGroundStick.generateWorld(random, chunkX * 16, chunkZ * 16, world, dimID, cg, cp);
            }
        }, 5);
    }
    
}