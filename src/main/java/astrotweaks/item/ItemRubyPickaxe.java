package astrotweaks.item;

import java.util.HashMap;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public final class ItemRubyPickaxe {
    public static final Item PICKAXE = new ItemPickaxe(EnumHelper.addToolMaterial("RUBY_PICKAXE", 3, 1500, 8f, 3.5f, 16)) {
        {
            this.attackDamage = 4f;
            this.attackSpeed = -2.7f;
        }
        public Set<String> getToolClasses(ItemStack stack) {
            HashMap<String, Integer> ret = new HashMap<String, Integer>();
            ret.put("pickaxe", 3);
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
    }.setRegistryName("astrotweaks", "ruby_pickaxe").setUnlocalizedName("ruby_pickaxe");

    private ItemRubyPickaxe() {}
}