
package astrotweaks.block;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.event.ModelRegistryEvent;

import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.NonNullList;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block;
import java.util.function.Predicate;

import java.util.Random;

import astrotweaks.creativetab.ATCreativeTabs;



public class BlockQuartzOreStone {
	public static final Block block = new BlockCustom().setRegistryName("astrotweaks", "quartz_ore_stone");
	
	private static final com.google.common.base.Predicate<IBlockState> STONE_MATCH =
	    state -> state != null && state.getBlock() == Blocks.STONE;

	public static void generateWorld(Random random, int chunkX, int chunkZ, World world, int dimID, IChunkGenerator cg, IChunkProvider cp) {
	    if (dimID != 0) return;
	    //if (!ModVariables.OW_Quartz_Gen) return;
	
	    WorldGenMinable gen = new WorldGenMinable(block.getDefaultState(), 4, STONE_MATCH);
	    for (int i = 0; i < 6; i++) {
	        int x = chunkX + random.nextInt(16);
	        int y = random.nextInt(35) + 25;
	        int z = chunkZ + random.nextInt(16);
	
	        gen.generate(world, random, new BlockPos(x, y, z));
	    }
	}
	

	
	public static class BlockCustom extends Block {
		public BlockCustom() {
			super(Material.ROCK);
			setUnlocalizedName("quartz_ore_stone");
			setSoundType(SoundType.STONE);
			setHarvestLevel("pickaxe", 1);
			setHardness(5F);
			setResistance(15F);
			setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
		}

		@Override
		public MapColor getMapColor(IBlockState state, IBlockAccess blockAccess, BlockPos pos) {
			return MapColor.STONE;
		}

		@Override
		public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
			drops.add(new ItemStack(Items.QUARTZ, (int) (1)));
		}
	}
}


















