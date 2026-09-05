package astrotweaks.item;

import java.util.HashMap;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

import astrotweaks.creativetab.ATCreativeTabs;

public final class ItemNeutroniumPickaxe {
    public static final Item PICKAXE = new ItemPickaxe(EnumHelper.addToolMaterial("NEUTRONIUM_PICKAXE", 6, 100000, 64f, 38f, 1)) {
        {
            this.attackSpeed = -2.7f;
        }
        public Set<String> getToolClasses(ItemStack stack) {
            HashMap<String, Integer> ret = new HashMap<String, Integer>();
            ret.put("pickaxe", 6);
            return ret.keySet();
        }
    }.setRegistryName("astrotweaks", "neutronium_pickaxe").setUnlocalizedName("neutronium_pickaxe").setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);

    private ItemNeutroniumPickaxe() {}
}