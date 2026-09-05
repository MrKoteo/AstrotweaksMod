package astrotweaks.item;

import java.util.HashMap;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public final class ItemEmeraldPickaxe {
    public static final Item PICKAXE = new ItemPickaxe(EnumHelper.addToolMaterial("EMERALD_PICKAXE", 3, 1000, 4f, 2.5f, 16)) {
        {
            this.attackDamage = 3.5f;
            this.attackSpeed = -2.8f;
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
                if (oreName.equals("gemEmerald")) {
                    return true;
                }
            }
            return false;
        }
    }.setRegistryName("astrotweaks", "emerald_pickaxe").setUnlocalizedName("emerald_pickaxe");

    private ItemEmeraldPickaxe() {}
}