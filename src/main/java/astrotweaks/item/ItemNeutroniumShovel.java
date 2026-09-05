package astrotweaks.item;

import java.util.HashMap;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

import astrotweaks.creativetab.ATCreativeTabs;

public final class ItemNeutroniumShovel {
    public static final Item SHOVEL = new ItemSpade(EnumHelper.addToolMaterial("NEUTRONIUM_SHOVEL", 6, 100000, 48f, 37.5f, 1)) {
        {
            this.attackSpeed = -2.9f;
        }
        public Set<String> getToolClasses(ItemStack stack) {
            HashMap<String, Integer> ret = new HashMap<String, Integer>();
            ret.put("spade", 6);
            return ret.keySet();
        }
    }.setRegistryName("astrotweaks", "neutronium_shovel").setUnlocalizedName("neutronium_shovel").setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);

    private ItemNeutroniumShovel() {}
}