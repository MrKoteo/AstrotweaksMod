package astrotweaks.item;

import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemFood;
import net.minecraft.item.Item;
import net.minecraft.item.EnumAction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.creativetab.CreativeTabs;

import astrotweaks.procedure.ProcedureSpoiledFoodEaten;

public final class ItemSpoiledPumpkinPie {
	public static final Item SPOILED_PUMPKIN_PIE = new ItemSpoiledPumpkinPie.ItemFoodCustom().setRegistryName("astrotweaks", "spoiled_pumpkin_pie").setUnlocalizedName("spoiled_pumpkin_pie");
	private ItemSpoiledPumpkinPie() {}
	public static class ItemFoodCustom extends ItemFood {
		public ItemFoodCustom() {
			super(2, 0f, false);
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
			ProcedureSpoiledFoodEaten.exect(entity);
		}
	}
}