package astrotweaks.item;

import java.util.HashMap;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public final class ItemBrassShovel {
    public static final Item SHOVEL = new ItemSpade(EnumHelper.addToolMaterial("BRASS_SHOVEL", 2, 200, 8f, 2.5f, 12)) {
        {
            this.attackDamage = 3f;
            this.attackSpeed = -3.0f;
        }
        public Set<String> getToolClasses(ItemStack stack) {
            HashMap<String, Integer> ret = new HashMap<String, Integer>();
            ret.put("spade", 2);
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
    }.setRegistryName("astrotweaks", "brass_shovel").setUnlocalizedName("brass_shovel");

    private ItemBrassShovel() {}
}