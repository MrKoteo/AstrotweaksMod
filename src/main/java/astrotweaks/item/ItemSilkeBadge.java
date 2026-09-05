package astrotweaks.item;

import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.client.util.ITooltipFlag;

import java.util.List;

import com.google.common.collect.Multimap;

import astrotweaks.creativetab.ATCreativeTabs;

public final class ItemSilkeBadge {
	public static final Item SILKE_BADGE = new ItemSilkeBadge.ItemCustom().setRegistryName("astrotweaks", "silke_badge").setUnlocalizedName("silke_badge");
	private ItemSilkeBadge() {}
	public static class ItemCustom extends Item {
		public ItemCustom() {
			maxStackSize = 1;
			setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
			setContainerItem(this);
		}

		@Override
		public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot slot) {
			Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(slot);
			if (slot == EntityEquipmentSlot.MAINHAND) {
				multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
						new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Item modifier", (double) -1, 0));
				multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Item modifier", -2.4, 0));
			}
			return multimap;
		}

		@Override
		public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag) {
			super.addInformation(itemstack, world, list, flag);
			list.add("Silke with u <3");
		}
	}
}