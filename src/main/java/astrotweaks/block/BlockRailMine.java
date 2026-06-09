package astrotweaks.block;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.event.ModelRegistryEvent;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.Item;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRail;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.Explosion;

import astrotweaks.creativetab.TabAstroTweaks;

import astrotweaks.ElementsAstrotweaksMod;

@ElementsAstrotweaksMod.ModElement.Tag
public class BlockRailMine extends ElementsAstrotweaksMod.ModElement {
	@GameRegistry.ObjectHolder("astrotweaks:rail_mine")
	public static final Block block = null;
	public BlockRailMine(ElementsAstrotweaksMod instance) {
		super(instance, 438);
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new BlockCustom().setRegistryName("rail_mine"));
		elements.items.add(() -> new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels(ModelRegistryEvent event) {
	    ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation("astrotweaks:rail_mine", "inventory"));
	}
	
	public static class BlockCustom extends BlockRail {
	    public BlockCustom() {
	        super();
	        setUnlocalizedName("rail_mine");
	        setSoundType(SoundType.METAL);
	        setHardness(0.7F);
	        setResistance(3.5F);
	        setCreativeTab(TabAstroTweaks.tab);
	    }
	
	    @Override
	    public void onMinecartPass(World world, EntityMinecart cart, BlockPos pos) {
	        if (world.isRemote) return;
            cart.setDead();
            initExp(world, pos);

	    }
	    
		//@Override public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
	    //	if (!world.isRemote) initExp(world, pos);
	    //}
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
	        //if (world.isRemote) return;
	        double x = pos.getX() + 0.5;
	        double y = pos.getY() + 0.5;
	        double z = pos.getZ() + 0.5;
	        // createExplosion(Entity exploder, double x, double y, double z, float strength, boolean createsFire)
	        world.setBlockToAir(pos);
	        world.createExplosion(null, x, y, z, 2.2F, true);
	    }


	
	    @SideOnly(Side.CLIENT)
	    @Override
	    public BlockRenderLayer getBlockLayer() {
	        return BlockRenderLayer.CUTOUT_MIPPED;
	    }
	    @Override
	    public boolean isOpaqueCube(IBlockState state) {
	        return false;
	    }
	}
}
