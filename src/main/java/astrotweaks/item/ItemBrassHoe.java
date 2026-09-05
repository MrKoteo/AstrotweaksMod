package astrotweaks.item;

import java.util.HashMap;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public final class ItemBrassHoe {
    public static final Item HOE = new ItemHoe(EnumHelper.addToolMaterial("BRASS_HOE", 2, 200, 8f, 3f, 12)) {
        public Set<String> getToolClasses(ItemStack stack) {
            HashMap<String, Integer> ret = new HashMap<String, Integer>();
            ret.put("hoe", 2);
            return ret.keySet();
        }
        @Override
        public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
            int[] oreIds = OreDictionary.getOreIDs(repair);
            for (int id : oreIds) {
                String oreName = OreDictionary.getOreName(id);
                if (oreName.equals("ingotBrass")) {
                    return true;
                }
            }
            return false;
        }
    }.setRegistryName("astrotweaks", "brass_hoe").setUnlocalizedName("brass_hoe");

    private ItemBrassHoe() {}
}