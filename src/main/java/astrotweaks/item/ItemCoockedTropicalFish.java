package astrotweaks.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public final class ItemCoockedTropicalFish {
    public static final Item TROPICAL_FISH = new ItemFood(3, 1.2000000000000002f, false) {
        @Override
        public EnumAction getItemUseAction(ItemStack par1ItemStack) {
            return EnumAction.EAT;
        }
    }.setAlwaysEdible().setCreativeTab(CreativeTabs.FOOD).setMaxStackSize(64).setRegistryName("astrotweaks", "coocked_tropical_fish").setUnlocalizedName("coocked_tropical_fish");

    private ItemCoockedTropicalFish() {}
}