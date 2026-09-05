package astrotweaks.item;

import java.util.HashMap;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

import astrotweaks.creativetab.ATCreativeTabs;

public final class ItemNeutroniumHoe {
    public static final Item HOE = new ItemHoe(EnumHelper.addToolMaterial("NEUTRONIUM_HOE", 6, 100000, 48f, 3.1f, 1)) {
        public Set<String> getToolClasses(ItemStack stack) {
            HashMap<String, Integer> ret = new HashMap<String, Integer>();
            ret.put("hoe", 6);
            return ret.keySet();
        }
    }.setRegistryName("astrotweaks", "neutronium_hoe").setUnlocalizedName("neutronium_hoe").setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);

    private ItemNeutroniumHoe() {}
}