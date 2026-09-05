package astrotweaks.item;

import java.util.HashMap;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

import astrotweaks.creativetab.ATCreativeTabs;

public final class ItemCrystalShovel {
    public static final Item SHOVEL = new ItemSpade(EnumHelper.addToolMaterial("CRYSTAL_SHOVEL", 4, 15000, 20f, 7.5f, 5)) {
        {
            this.attackSpeed = -2.9f;
        }
        public Set<String> getToolClasses(ItemStack stack) {
            HashMap<String, Integer> ret = new HashMap<String, Integer>();
            ret.put("spade", 4);
            return ret.keySet();
        }
    }.setRegistryName("astrotweaks", "crystal_shovel").setUnlocalizedName("crystal_shovel").setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);

    private ItemCrystalShovel() {}
}