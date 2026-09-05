package astrotweaks.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public final class ItemBronzeSaw {
    public static final Item BRONZE_SAW = new ItemAxe(EnumHelper.addToolMaterial("BRONZE_SAW", 2, 250, 8f, 2f, 12), 2f, -2.7f) {
        @Override
        public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
            int[] oreIds = OreDictionary.getOreIDs(repair);
            for (int id : oreIds) {
                String oreName = OreDictionary.getOreName(id);
                if (oreName.equals("ingotBronze")) {
                    return true;
                }
            }
            return false;
        }
    }.setRegistryName("astrotweaks", "bronze_saw").setUnlocalizedName("bronze_saw");

    private ItemBronzeSaw() {}
}