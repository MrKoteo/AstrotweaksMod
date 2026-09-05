package astrotweaks.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public final class ItemSawIron {
    public static final Item IRON_SAW = new ItemAxe(EnumHelper.addToolMaterial("IRON_SAW", 2, 250, 8f, 2f, 14), 2f, -2.7f) {
        @Override
        public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
            int[] oreIds = OreDictionary.getOreIDs(repair);
            for (int id : oreIds) {
                String oreName = OreDictionary.getOreName(id);
                if (oreName.equals("ingotIron")) {
                    return true;
                }
            }
            return false;
        }
    }.setRegistryName("astrotweaks", "iron_saw").setUnlocalizedName("iron_saw");

    private ItemSawIron() {}
}