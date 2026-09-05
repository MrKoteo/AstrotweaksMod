package astrotweaks.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public final class ItemDryBread {
    public static final Item DRY_BREAD = new ItemFood(4, 0.5f, false) {
        @Override
        public int getMaxItemUseDuration(ItemStack stack) {
            return 96;
        }
        @Override
        public EnumAction getItemUseAction(ItemStack par1ItemStack) {
            return EnumAction.EAT;
        }
    }.setCreativeTab(CreativeTabs.FOOD).setRegistryName("astrotweaks", "dry_bread").setUnlocalizedName("dry_bread");

    private ItemDryBread() {}
}