package astrotweaks.item;

import java.util.HashMap;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public final class ItemRubyAxe {
    public static final Item AXE = new ItemAxe(EnumHelper.addToolMaterial("RUBY_AXE", 3, 1500, 8f, 8.5f, 16), 8.5f, -2.9f) {

        @Override
        public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
            int[] oreIds = OreDictionary.getOreIDs(repair);
            for (int id : oreIds) {
                String oreName = OreDictionary.getOreName(id);
                if (oreName.equals("gemRuby") || oreName.equals("ruby")) {
                    return true;
                }
            }
            return false;
        }
        public Set<String> getToolClasses(ItemStack stack) {
            HashMap<String, Integer> ret = new HashMap<String, Integer>();
            ret.put("axe", 3);
            return ret.keySet();
        }
    }.setRegistryName("astrotweaks", "ruby_axe").setUnlocalizedName("ruby_axe");

    private ItemRubyAxe() {}
}