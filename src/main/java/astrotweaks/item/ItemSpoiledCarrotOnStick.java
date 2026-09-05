package astrotweaks.item;

import net.minecraft.item.Item;

public final class ItemSpoiledCarrotOnStick {
	public static final Item SPOILED_CARROT_ON_STICK = new ItemSpoiledCarrotOnStick.ItemCustom().setRegistryName("astrotweaks", "spoiled_carrot_on_stick").setUnlocalizedName("spoiled_carrot_on_stick");
	private ItemSpoiledCarrotOnStick() {}
	public static class ItemCustom extends Item {
		public ItemCustom() {
			maxStackSize = 1;
			setCreativeTab(null);
		}

	}
}