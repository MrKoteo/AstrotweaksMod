package astrotweaks.item;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;

import net.minecraft.init.Blocks;

import astrotweaks.creativetab.ATCreativeTabs;

public final class ItemUDestroyerItem {
	public static final Item DESTROYER_ITEM = new ItemUDestroyerItem.ItemCustom().setRegistryName("astrotweaks", "u_destroyer_item").setUnlocalizedName("u_destroyer_item");
	private ItemUDestroyerItem() {}
	public static class ItemCustom extends Item {
		public ItemCustom() {
			setMaxDamage(1);
			setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
		}

		@Override
		public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, EnumHand hand) {
            if (world.isRemote) {
                return EnumActionResult.PASS;
            }
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
            if (!player.capabilities.isCreativeMode) {
                ItemStack heldStack = player.getHeldItem(hand);
                heldStack.shrink(1);
            }
            return EnumActionResult.SUCCESS;
        }
        @Override
        public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
            super.hitEntity(stack, target, attacker);
            if (!attacker.world.isRemote) {
                target.attackEntityFrom(DamageSource.GENERIC, 404.0f);
                if (!(attacker instanceof EntityPlayer && ((EntityPlayer) attacker).capabilities.isCreativeMode)) {
                    stack.shrink(1);
                }
            }
            return true;
        }
    }
}
