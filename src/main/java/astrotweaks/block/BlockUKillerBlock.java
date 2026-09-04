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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.Item;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block;
import net.minecraft.util.math.AxisAlignedBB;

import astrotweaks.creativetab.ATCreativeTabs;

import astrotweaks.ElementsAstrotweaksMod;

import java.util.List;

@ElementsAstrotweaksMod.ModElement.Tag
public class BlockUKillerBlock extends ElementsAstrotweaksMod.ModElement {
	@GameRegistry.ObjectHolder("astrotweaks:u_killer_block")
	public static final Block block = null;
	public BlockUKillerBlock(ElementsAstrotweaksMod instance) {
		super(instance, 444);
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new BlockCustom().setRegistryName("u_killer_block"));
		elements.items.add(() -> new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
				new ModelResourceLocation("astrotweaks:u_killer_block", "inventory"));
	}
	public static class BlockCustom extends Block {
		public BlockCustom() {
			super(Material.IRON, MapColor.IRON);
			setUnlocalizedName("u_killer_block");
			setSoundType(SoundType.METAL);
			setHardness(1000F);
			setResistance(1000F);
			setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
			setBlockUnbreakable();
		}
		@Override
		public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos fromPos) {
			super.neighborChanged(state, world, pos, neighborBlock, fromPos);
			if (!world.isRemote && world.isBlockIndirectlyGettingPowered(pos) > 0) {
				KillXArea(world, pos);
			}
		}
		@Override
		public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer entity, EnumHand hand, EnumFacing direction,
				float hitX, float hitY, float hitZ) {
			if (!world.isRemote) {
				KillXArea(world, pos);
			}
			return true;
		}
		private static void KillXArea(World world, BlockPos pos) {
			if (world == null || pos == null || world.isRemote) return;
			// Define 21x21x21 cube centered on block: from -10 to +10 inclusive
			AxisAlignedBB box = new AxisAlignedBB(
					pos.getX() - 10, pos.getY() - 10, pos.getZ() - 10,
					pos.getX() + 11, pos.getY() + 11, pos.getZ() + 11); // +11 because AABB is exclusive on max side

			List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, box, e -> !(e instanceof EntityPlayer));
			for (Entity e : entities) {
				if (!e.isDead) {
					try {
						e.setDead();
					} catch (NoSuchMethodError ex) {
						// ignore
					}
				}
			}
			// remove the block itself
			world.setBlockToAir(pos);
		}
	}
}
