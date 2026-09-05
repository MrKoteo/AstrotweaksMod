package astrotweaks.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public final class ItemTinSaw {
    public static final Item TIN_SAW = new ItemAxe(EnumHelper.addToolMaterial("TIN_SAW", 1, 100, 6f, 1f, 10), 2f, -2.7f) {
        @Override
        public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
            int[] oreIds = OreDictionary.getOreIDs(repair);
            for (int id : oreIds) {
                String oreName = OreDictionary.getOreName(id);
                if (oreName.equals("ingotTin")) {
                    return true;
                }
            }
            return false;
        }
    }.setRegistryName("astrotweaks", "tin_saw").setUnlocalizedName("tin_saw");

    private ItemTinSaw() {}
}
