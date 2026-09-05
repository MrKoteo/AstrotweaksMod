package astrotweaks.item;

import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemFood;
import net.minecraft.item.Item;
import net.minecraft.item.EnumAction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.creativetab.CreativeTabs;

import astrotweaks.procedure.ProcedureSpoiledFoodEaten;

public final class ItemSpoiledDrink {
	public static final Item SPOILED_DRINK = new ItemSpoiledDrink.ItemFoodCustom().setRegistryName("astrotweaks", "spoiled_drink").setUnlocalizedName("spoiled_drink");
	private ItemSpoiledDrink() {}
	public static class ItemFoodCustom extends ItemFood {
		public ItemFoodCustom() {
			super(1, 0f, false);
			setCreativeTab(CreativeTabs.FOOD);
			setMaxStackSize(16);
		}

		@Override
		public int getMaxItemUseDuration(ItemStack stack) {
			return 64;
		}

		@Override
		public EnumAction getItemUseAction(ItemStack par1ItemStack) {
			return EnumAction.DRINK;
		}

		@Override
		protected void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entity) {
			super.onFoodEaten(itemStack, world, entity);
			ProcedureSpoiledFoodEaten.exect(entity);
		}
	}
}