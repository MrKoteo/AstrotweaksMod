package astrotweaks.item;

import java.util.HashMap;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public final class ItemEmeraldShovel {
    public static final Item SHOVEL = new ItemSpade(EnumHelper.addToolMaterial("EMERALD_SHOVEL", 3, 1000, 8f, 2.5f, 16)) {
        {
            this.attackDamage = 4f;
            this.attackSpeed = -3.0f;
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
                if (oreName.equals("gemEmerald")) {
                    return true;
                }
            }
            return false;
        }
    }.setRegistryName("astrotweaks", "emerald_shovel").setUnlocalizedName("emerald_shovel");

    private ItemEmeraldShovel() {}
}