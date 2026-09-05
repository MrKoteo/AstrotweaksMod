package astrotweaks.item;

import java.util.HashMap;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public final class ItemRubyHoe {
    public static final Item HOE = new ItemHoe(EnumHelper.addToolMaterial("RUBY_HOE", 3, 1500, 8f, 3.1f, 16)) {
        public Set<String> getToolClasses(ItemStack stack) {
            HashMap<String, Integer> ret = new HashMap<String, Integer>();
            ret.put("hoe", 3);
            return ret.keySet();
        }
        @Override
        public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
            int[] oreIds = OreDictionary.getOreIDs(repair);
            for (int id : oreIds) {
                String oreName = OreDictionary.getOreName(id);
                if (oreName.equals("gemRuby")) {
                    return true;
                }
            }
            return false;
        }

    }.setRegistryName("astrotweaks", "ruby_hoe").setUnlocalizedName("ruby_hoe");

    private ItemRubyHoe() {}
}