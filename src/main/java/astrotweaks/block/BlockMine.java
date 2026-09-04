package astrotweaks.block;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.event.ModelRegistryEvent;

import net.minecraft.world.IBlockAccess;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.Item;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.Explosion;

import astrotweaks.creativetab.ATCreativeTabs;

import astrotweaks.ElementsAstrotweaksMod;

@ElementsAstrotweaksMod.ModElement.Tag
public class BlockMine extends ElementsAstrotweaksMod.ModElement {
	@GameRegistry.ObjectHolder("astrotweaks:mine")
	public static final Block block = null;
	public BlockMine(ElementsAstrotweaksMod instance) {
		super(instance, 436);
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new BlockCustom().setRegistryName("mine"));
		elements.items.add(() -> new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation("astrotweaks:mine", "inventory"));
	}
	public static class BlockCustom extends BlockFalling {
		public BlockCustom() {
			super(Material.CLOTH);
			setUnlocalizedName("mine");
			setSoundType(SoundType.METAL);
			setHarvestLevel("shovel", 0);
			setHardness(1F);
			setResistance(5F);
			setLightOpacity(0);
			setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
		}

		@SideOnly(Side.CLIENT)
		@Override
		public BlockRenderLayer getBlockLayer() {
			return BlockRenderLayer.CUTOUT_MIPPED;
		}
		@Override
		public boolean isFullCube(IBlockState state) {
			return false;
		}
		@Override
		public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
			return new AxisAlignedBB(0.3125, 0.0, 0.3125, 0.6875, 0.125, 0.6875);
		}
		@Override
		public boolean isOpaqueCube(IBlockState state) {
			return false;
		}
		@Override
		public MapColor getMapColor(IBlockState state, IBlockAccess blockAccess, BlockPos pos) {
			return MapColor.AIR;
		}



		@Override
		public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
			super.onEntityCollidedWithBlock(world, pos, state, entity);
			if (!world.isRemote) initExp(world, pos);
		}
	    @Override public void onBlockClicked(World world, BlockPos pos, EntityPlayer entity) {
	        super.onBlockClicked(world, pos, entity);
	        if (!world.isRemote) initExp(world, pos);
	    }
	    @Override
	    public boolean onBlockActivated(World world,BlockPos pos,IBlockState state, EntityPlayer entity,EnumHand hand,EnumFacing direction, float hitX, float hitY, float hitZ) {
	        super.onBlockActivated(world, pos, state, entity, hand, direction, hitX, hitY, hitZ);
	        if (!world.isRemote) initExp(world, pos);
	        return true;
	    }
	    private static void initExp(World world, BlockPos pos) {
	        if (world.isRemote) return;
	        double x = pos.getX() + 0.5;
	        double y = pos.getY() + 0.75;
	        double z = pos.getZ() + 0.5;
	        // createExplosion(Entity exploder, double x, double y, double z, float strength, boolean createsFire)
	        world.setBlockToAir(pos);
	        world.createExplosion(null, x, y, z, 2.2F, true);
	    }
	}
}
