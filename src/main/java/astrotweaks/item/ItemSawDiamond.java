package astrotweaks.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public final class ItemSawDiamond {
    public static final Item DIAMOND_SAW = new ItemAxe(EnumHelper.addToolMaterial("DIAMOND_SAW", 3, 1561, 10f, 2f, 10), 2f, -2.7f) {
        @Override
        public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
            int[] oreIds = OreDictionary.getOreIDs(repair);
            for (int id : oreIds) {
                String oreName = OreDictionary.getOreName(id);
                if (oreName.equals("gemDiamond")) {
                    return true;
                }
            }
            return false;
        }
    }.setRegistryName("astrotweaks", "diamond_saw").setUnlocalizedName("diamond_saw");

    private ItemSawDiamond() {}
}