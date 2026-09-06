
package astrotweaks.block;

import net.minecraftforge.oredict.OreDictionary;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block;
import net.minecraft.util.NonNullList;

import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;

import astrotweaks.util.DropHandler;
import astrotweaks.util.DropHandler.DropEntry;

import astrotweaks.ModVariables;
import astrotweaks.creativetab.ATCreativeTabs;



public class BlockMineralsOre {
	public static final Block block = new BlockCustom().setRegistryName("astrotweaks", "minerals_ore");

	private static final com.google.common.base.Predicate<IBlockState> STONE_MATCH = state -> state != null && state.getBlock() == Blocks.STONE;

	public static void generateWorld(Random random, int chunkX, int chunkZ, World world, int dimID, IChunkGenerator cg, IChunkProvider cp) {
	    if (dimID != 0) return;
	    //if (!ModVariables.OW_Minerals_Gen) return;
	
	    WorldGenMinable gen = new WorldGenMinable(block.getDefaultState(), 8, STONE_MATCH);
	    BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
	
	    for (int i = 0; i < 5; i++) {
	        int x = chunkX + random.nextInt(16);
	        int y = random.nextInt(37) + 3;
	        int z = chunkZ + random.nextInt(16);
	
	        pos.setPos(x, y, z);
	        gen.generate(world, random, pos);
	    }
	}

	public static class BlockCustom extends Block {
		public BlockCustom() {
			super(Material.ROCK);
			setUnlocalizedName("minerals_ore");
			setSoundType(SoundType.STONE);
			setHarvestLevel("pickaxe", 2);
			setHardness(2F);
			setResistance(10F);
			setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
		}
        private static final DropEntry[] DE_TABLE = new DropEntry[] {
            new DropEntry("oreIron",      1, 11.0),
            new DropEntry("oreLead",      1, 5.0),
            new DropEntry("oreThorium",   1, 5.0),
            new DropEntry("oreCobalt",    1, 20.0),
            new DropEntry("oreNickel",    1, 20.0),
            new DropEntry("oreTitanium",  1, 22.0),
            new DropEntry("oreNiobium",   1, 42.0)
        };
		private static final DropHandler DE_DROP_TABLE = new DropHandler(DE_TABLE);
		@Override public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		    World w = (world instanceof World) ? (World) world : null;
		    Random rng = (w != null) ? w.rand : ThreadLocalRandom.current();

		    int rolls = rng.nextInt(2) + 1;
		    DE_DROP_TABLE.generateDrops(drops, w, rolls);
		}
	}
}
