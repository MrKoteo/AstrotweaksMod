package astrotweaks.item;

import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemFood;
import net.minecraft.item.Item;
import net.minecraft.item.EnumAction;
import net.minecraft.entity.player.EntityPlayer;

import astrotweaks.procedure.ProcedureSpoiledFoodEaten;

public final class ItemSpoiledFishPufferfish1 {
	public static final Item SPOILED_PUFFERFISH = new ItemSpoiledFishPufferfish1.ItemFoodCustom().setRegistryName("astrotweaks", "spoiled_fish_pufferfish").setUnlocalizedName("spoiled_fish_pufferfish");
	private ItemSpoiledFishPufferfish1() {}
	public static class ItemFoodCustom extends ItemFood {
		public ItemFoodCustom() {
			super(1, 0f, false);
			setCreativeTab(null);
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