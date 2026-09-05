package astrotweaks.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public final class ItemMilkBottle {
    public static final Item MILK_BOTTLE = new ItemFood(1, 0.4f, false) {
        @Override
        public int getMaxItemUseDuration(ItemStack stack) {
            return 24;
        }
        @Override
        public EnumAction getItemUseAction(ItemStack par1ItemStack) {
            return EnumAction.DRINK;
        }
    }.setCreativeTab(CreativeTabs.FOOD).setMaxStackSize(16).setRegistryName("astrotweaks", "milk_bottle").setUnlocalizedName("milk_bottle");

    private ItemMilkBottle() {}
}