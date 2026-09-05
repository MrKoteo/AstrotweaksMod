package astrotweaks.item;

import java.util.HashMap;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

import astrotweaks.creativetab.ATCreativeTabs;

public final class ItemCrystalHoe {
    public static final Item HOE = new ItemHoe(EnumHelper.addToolMaterial("CRYSTAL_HOE", 4, 15000, 20f, 3.1f, 5)) {
        public Set<String> getToolClasses(ItemStack stack) {
            HashMap<String, Integer> ret = new HashMap<String, Integer>();
            ret.put("hoe", 4);
            return ret.keySet();
        }
    }.setRegistryName("astrotweaks", "crystal_hoe").setUnlocalizedName("crystal_hoe").setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);

    private ItemCrystalHoe() {}
}