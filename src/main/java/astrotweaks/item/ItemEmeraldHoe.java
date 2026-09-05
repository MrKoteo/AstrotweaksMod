package astrotweaks.item;

import java.util.HashMap;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public final class ItemEmeraldHoe {
    public static final Item HOE = new ItemHoe(EnumHelper.addToolMaterial("EMERALD_HOE", 3, 1000, 8f, 3f, 16)) {
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
                if (oreName.equals("gemEmerald")) {
                    return true;
                }
            }
            return false;
        }
    }.setRegistryName("astrotweaks", "emerald_hoe").setUnlocalizedName("emerald_hoe");

    private ItemEmeraldHoe() {}
}