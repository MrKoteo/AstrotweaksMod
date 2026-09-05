package astrotweaks.block;

import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.World;

import java.util.Random;

import astrotweaks.ElementsAstrotweaksMod;

@ElementsAstrotweaksMod.ModElement.Tag
public class BlockWorldGen extends ElementsAstrotweaksMod.ModElement {
	public BlockWorldGen(ElementsAstrotweaksMod instance) {
		super(instance, 1000);
	}

	@Override
	public void generateWorld(Random random, int chunkX, int chunkZ, World world, int dimID, IChunkGenerator cg, IChunkProvider cp) {
		BlockRubyOre.generateWorld(random, chunkX, chunkZ, world, dimID, cg, cp);
		BlockMineralsOre.generateWorld(random, chunkX, chunkZ, world, dimID, cg, cp);
		BlockGroundRock1.generateWorld(random, chunkX, chunkZ, world, dimID, cg, cp);
		BlockGroundRock2.generateWorld(random, chunkX, chunkZ, world, dimID, cg, cp);
		BlockGroundStick.generateWorld(random, chunkX, chunkZ, world, dimID, cg, cp);
		BlockQuartzOreStone.generateWorld(random, chunkX, chunkZ, world, dimID, cg, cp);
		BlockQuartzOreGranite.generateWorld(random, chunkX, chunkZ, world, dimID, cg, cp);
	}
}