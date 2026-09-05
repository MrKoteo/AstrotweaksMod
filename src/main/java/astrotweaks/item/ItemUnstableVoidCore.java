package astrotweaks.item;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.block.state.IBlockState;

import com.google.common.collect.Multimap;

import astrotweaks.creativetab.ATCreativeTabs;

public final class ItemUnstableVoidCore {
	public static final Item UNSTABLE_VOID_CORE = new ItemUnstableVoidCore.ItemCustom().setRegistryName("astrotweaks", "unstable_void_core").setUnlocalizedName("unstable_void_core");
	private ItemUnstableVoidCore() {}
	public static class ItemCustom extends Item {
		public ItemCustom() {
			setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
		}

		@Override
		public float getDestroySpeed(ItemStack par1ItemStack, IBlockState par2Block) {
			return 2F;
		}

		@Override
		public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot slot) {
			Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(slot);
			if (slot == EntityEquipmentSlot.MAINHAND) {
				multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
						new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Item modifier", (double) 4, 0));
				multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Item modifier", -2.4, 0));
			}
			return multimap;
		}
	}
}