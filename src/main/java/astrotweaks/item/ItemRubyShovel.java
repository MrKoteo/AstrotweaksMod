package astrotweaks.item;

import java.util.HashMap;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public final class ItemRubyShovel {
    public static final Item SHOVEL = new ItemSpade(EnumHelper.addToolMaterial("RUBY_SHOVEL", 3, 1500, 8f, 3.5f, 16)) {
        {
            this.attackDamage = 5f;
            this.attackSpeed = -2.9f;
        }
        public Set<String> getToolClasses(ItemStack stack) {
            HashMap<String, Integer> ret = new HashMap<String, Integer>();
            ret.put("spade", 3);
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

    }.setRegistryName("astrotweaks", "ruby_shovel").setUnlocalizedName("ruby_shovel");

    private ItemRubyShovel() {}
}