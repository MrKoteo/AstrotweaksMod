package astrotweaks.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public final class ItemCopperSaw {
    public static final Item COPPER_SAW = new ItemAxe(EnumHelper.addToolMaterial("COPPER_SAW", 1, 160, 5f, 2f, 12), 2f, -2.7f) {
        @Override
        public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
            int[] oreIds = OreDictionary.getOreIDs(repair);
            for (int id : oreIds) {
                String oreName = OreDictionary.getOreName(id);
                if (oreName.equals("ingotCopper")) {
                    return true;
                }
            }
            return false;
        }
    }.setRegistryName("astrotweaks", "copper_saw").setUnlocalizedName("copper_saw");

    private ItemCopperSaw() {}
}