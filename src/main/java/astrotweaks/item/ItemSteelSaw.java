package astrotweaks.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public final class ItemSteelSaw {
    public static final Item STEEL_SAW = new ItemAxe(EnumHelper.addToolMaterial("STEEL_SAW", 3, 1000, 10f, 2f, 12), 2f, -2.7f) {
        @Override
        public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
            int[] oreIds = OreDictionary.getOreIDs(repair);
            for (int id : oreIds) {
                String oreName = OreDictionary.getOreName(id);
                if (oreName.equals("ingotSteel")) {
                    return true;
                }
            }
            return false;
        }
    }.setRegistryName("astrotweaks", "steel_saw").setUnlocalizedName("steel_saw");

    private ItemSteelSaw() {}
}