package astrotweaks.item;

import java.util.HashMap;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public final class ItemBrassPickaxe {
    public static final Item PICKAXE = new ItemPickaxe(EnumHelper.addToolMaterial("BRASS_PICKAXE", 2, 200, 4f, 2f, 12)) {
        {
            this.attackDamage = 4f;
            this.attackSpeed = -2.8f;
        }
        public Set<String> getToolClasses(ItemStack stack) {
            HashMap<String, Integer> ret = new HashMap<String, Integer>();
            ret.put("pickaxe", 2);
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
    }.setRegistryName("astrotweaks", "brass_pickaxe").setUnlocalizedName("brass_pickaxe");

    private ItemBrassPickaxe() {}
}