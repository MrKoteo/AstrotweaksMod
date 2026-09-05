package astrotweaks.item;

import java.util.HashMap;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public final class ItemBrassAxe {
    public static final Item AXE = new ItemAxe(EnumHelper.addToolMaterial("BRASS_AXE", 2, 200, 8f, 8f, 12), 8f, -3f) {
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
        public Set<String> getToolClasses(ItemStack stack) {
            HashMap<String, Integer> ret = new HashMap<String, Integer>();
            ret.put("axe", 2);
            return ret.keySet();
        }
    }.setRegistryName("astrotweaks", "brass_axe").setUnlocalizedName("brass_axe");

    private ItemBrassAxe() {}
}