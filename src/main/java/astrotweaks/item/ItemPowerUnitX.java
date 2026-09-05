package astrotweaks.item;

import net.minecraft.item.Item;

import astrotweaks.creativetab.ATCreativeTabs;

public final class ItemPowerUnitX {
	public static final Item POWER_UNIT_X = new ItemPowerUnitX.ItemCustom().setRegistryName("astrotweaks", "power_unit_x").setUnlocalizedName("power_unit_x");
	private ItemPowerUnitX() {}
	public static class ItemCustom extends Item {
		public ItemCustom() {
			setMaxDamage(7);
			setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
		}

	}
}