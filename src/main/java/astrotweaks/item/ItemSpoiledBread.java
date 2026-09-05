package astrotweaks.item;

import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemFood;
import net.minecraft.item.Item;
import net.minecraft.item.EnumAction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.creativetab.CreativeTabs;

import astrotweaks.procedure.ProcedureSpoiledBreadEaten;

public final class ItemSpoiledBread {
	public static final Item SPOILED_BREAD = new ItemSpoiledBread.ItemFoodCustom().setRegistryName("astrotweaks", "spoiled_bread").setUnlocalizedName("spoiled_bread");
	private ItemSpoiledBread() {}
	public static class ItemFoodCustom extends ItemFood {
		public ItemFoodCustom() {
			super(3, 0.2f, false);
			setCreativeTab(CreativeTabs.FOOD);
			setMaxStackSize(64);
		}

		@Override
		public int getMaxItemUseDuration(ItemStack stack) {
			return 64;
		}

		@Override
		public EnumAction getItemUseAction(ItemStack par1ItemStack) {
			return EnumAction.EAT;
		}

		@Override
		protected void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entity) {
			super.onFoodEaten(itemStack, world, entity);
			ProcedureSpoiledBreadEaten.executeProcedure(entity);
		}
	}
}