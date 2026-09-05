package astrotweaks.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public final class ItemGoldenSaw {
    public static final Item GOLDEN_SAW = new ItemAxe(EnumHelper.addToolMaterial("GOLDEN_SAW", 0, 32, 6f, 0f, 22), 2f, -2.7f) {
        @Override
        public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
            int[] oreIds = OreDictionary.getOreIDs(repair);
            for (int id : oreIds) {
                String oreName = OreDictionary.getOreName(id);
                if (oreName.equals("ingotGold")) {
                    return true;
                }
            }
            return false;
        }
    }.setRegistryName("astrotweaks", "golden_saw").setUnlocalizedName("golden_saw");

    private ItemGoldenSaw() {}
}