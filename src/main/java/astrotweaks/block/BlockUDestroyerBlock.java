package astrotweaks.block;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.event.ModelRegistryEvent;

import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import net.minecraft.item.ItemBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import astrotweaks.creativetab.ATCreativeTabs;
import astrotweaks.ElementsAstrotweaksMod;


public class BlockUDestroyerBlock {
	public static final Block block = new BlockCustom().setRegistryName("astrotweaks", "u_destroyer_block");

	public static class BlockCustom extends Block {
		public BlockCustom() {
			super(Material.IRON, MapColor.IRON);
			setUnlocalizedName("u_destroyer_block");
			setSoundType(SoundType.METAL);
			setHardness(1000F);
			setResistance(1000F);
			setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
			setBlockUnbreakable();
		}
		@Override
		public net.minecraft.block.material.EnumPushReaction getMobilityFlag(IBlockState state) {
			return net.minecraft.block.material.EnumPushReaction.IGNORE;
		}
		@Override
		public MapColor getMapColor(IBlockState state, IBlockAccess blockAccess, BlockPos pos) {
			return MapColor.AIR;
		}
		@Override
		public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos fromPos) {
			super.neighborChanged(state, world, pos, neighborBlock, fromPos);
			if (world.isBlockIndirectlyGettingPowered(pos) > 0) {
				DelXArea(world, pos);
			}
		}
		@Override
		public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer entity, EnumHand hand, EnumFacing direction,
				float hitX, float hitY, float hitZ) {
			super.onBlockActivated(world, pos, state, entity, hand, direction, hitX, hitY, hitZ);
			DelXArea(world, pos);
			return true;
		}
		// Execute several fill commands to clear stacked regions around pos (server-side)
		private static void DelXArea(World world, BlockPos pos) {
			if (world == null || pos == null || world.isRemote) return;
			
			//MinecraftServer server = world.getMinecraftServer();
			//if (server == null) return;

			final int r = 10; // radius
			int worldMaxY = 255;
			try {
				worldMaxY = world.getHeight();
			} catch (Throwable t) {
				///
			}
			
			for (int dy = -r; dy <= r; dy++) {
				int y = pos.getY() + dy;
		
				if (y < 1) continue;
				if (y >= worldMaxY) continue;
		
				for (int dx = -r; dx <= r; dx++) {
					for (int dz = -r; dz <= r; dz++) {
						BlockPos p = new BlockPos(pos.getX() + dx, y, pos.getZ() + dz);
		
						//if (!world.isBlockLoaded(p)) continue;
		
						IBlockState state = world.getBlockState(p);
						Block b = state.getBlock();
		
						if (b == Blocks.AIR) continue;
						
						//if (b == Blocks.BEDROCK) continue;
						//TileEntity te = world.getTileEntity(p);
						//if (te != null) continue;

						world.setBlockToAir(p);
					}
				}
			}

			//world.setBlockToAir(pos);
		}
	}
}
