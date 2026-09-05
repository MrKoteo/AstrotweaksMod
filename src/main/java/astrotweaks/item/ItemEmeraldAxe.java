package astrotweaks.item;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public final class ItemEmeraldAxe {
    public static final Item AXE = new ItemAxe(EnumHelper.addToolMaterial("EMERALD_AXE", 3, 1000, 8.0F, 8.0F, 16), 7.5F, -3.0F) {
        @Override
        public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
            for (int id : OreDictionary.getOreIDs(repair)) {
                if ("gemEmerald".equals(OreDictionary.getOreName(id))) {
                    return true;
                }
            }
            return false;
        }
        @Override
        public Set<String> getToolClasses(ItemStack stack) {
            Map<String, Integer> tools = new HashMap<>();
            tools.put("axe", 3);
            return tools.keySet();
        }
    }.setRegistryName("astrotweaks", "emerald_axe").setUnlocalizedName("emerald_axe");

    private ItemEmeraldAxe() {}
}
