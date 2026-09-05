package astrotweaks.creativetab;

import astrotweaks.item.ItemStrangeQuant;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// import astrotweaks.init.ModBlocks

public final class ATCreativeTabs {
    public static final CreativeTabs ASTRO_TWEAKS_CT =
        new CreativeTabs("astro_tweaks_ct") {
            @Override
            @SideOnly(Side.CLIENT)
            public ItemStack getTabIconItem() {
                return new ItemStack(astrotweaks.item.ATItems.SCIENCE, 1);
            }
            /*
            @SideOnly(Side.CLIENT)
            public boolean hasSearchBar() {
                return false;
            }
            */
        };
    public static final CreativeTabs AT_INTEGRATION_CT =
		new CreativeTabs("at_integration_ct") {
			@SideOnly(Side.CLIENT)
			@Override
			public ItemStack getTabIconItem() {
				return new ItemStack(ItemStrangeQuant.STRANGE_QUANT, 1);
			}
			/*
			@SideOnly(Side.CLIENT)
			public boolean hasSearchBar() {
				return false;
			}
			*/
        };
    public static void init() {}

    private ATCreativeTabs() {}
}
