package astrotweaks.item;

import java.util.HashMap;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

import astrotweaks.creativetab.ATCreativeTabs;

public final class ItemCrystalPickaxe {
    public static final Item PICKAXE = new ItemPickaxe(EnumHelper.addToolMaterial("CRYSTAL_PICKAXE", 4, 15000, 20f, 8f, 5)) {
        {
            this.attackSpeed = -2.7f;
        }
        public Set<String> getToolClasses(ItemStack stack) {
            HashMap<String, Integer> ret = new HashMap<String, Integer>();
            ret.put("pickaxe", 4);
            return ret.keySet();
        }
    }.setRegistryName("astrotweaks", "crystal_pickaxe").setUnlocalizedName("crystal_pickaxe").setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);

    private ItemCrystalPickaxe() {}
}