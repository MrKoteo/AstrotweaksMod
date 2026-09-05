package astrotweaks.item;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.ActionResult;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;

public final class ItemXpBoxU {
	public static final Item XP_BOX = new ItemXpBoxU.ItemCustom().setRegistryName("astrotweaks", "xp_box").setUnlocalizedName("xp_box");
	private ItemXpBoxU() {}
	public static class ItemCustom extends Item {
		public ItemCustom() {}

		@Override
		@SideOnly(Side.CLIENT)
		public boolean hasEffect(ItemStack itemstack) {
			return true;
		}
		@Override
		public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer entity, EnumHand hand) {
			ItemStack itemstack = entity.getHeldItem(hand);
			if (!world.isRemote) {
				OpenBox(entity, itemstack);
				itemstack.shrink(1);
			}
			return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
		}
		@Override
		public EnumActionResult onItemUseFirst(EntityPlayer entity, World world, BlockPos pos, EnumFacing direction, float hitX, float hitY,
				float hitZ, EnumHand hand) {
			ItemStack itemstack = entity.getHeldItem(hand);
			if (!world.isRemote) {
				OpenBox(entity, itemstack);
				itemstack.shrink(1);
			}
			return EnumActionResult.SUCCESS;
		}

		private static void OpenBox(Entity entity, ItemStack itemstack) {
			double XpAmount = 10;
			boolean Type = false;
			XpAmount = itemstack.hasTagCompound() ? itemstack.getTagCompound().getDouble("xp") : 50;
			Type = itemstack.hasTagCompound() ? itemstack.getTagCompound().getBoolean("levels") : false;

			if (entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;
				if (Type) {
					player.addExperienceLevel((int) XpAmount);
				} else {
					player.addExperience((int) XpAmount);
				}
			}
		}
	}
}
