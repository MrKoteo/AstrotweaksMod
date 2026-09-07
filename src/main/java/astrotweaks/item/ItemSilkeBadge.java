package astrotweaks.item;

import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.item.Item;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
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
				multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),new AttributeModifier(ATTACK_DAMAGE_MODIFIER,"Item modifier", (double) -1, 0));
				multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Item modifier", -2.4, 0));
			}
			return multimap;
		}

		@Override
		public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag) {
			super.addInformation(itemstack, world, list, flag);
			list.add("Silke with u <3");
		}

		// При нажатии ПКМ выдаём регенерацию I уровня на 1 секунду
		@Override
		public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
			ItemStack stack = player.getHeldItem(hand);
			if (!world.isRemote) {
				// Длительность 20 тиков = 1 секунда, amplifier 0 = уровень I
				player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 20, 0, false, false));
			}
			return new ActionResult<>(EnumActionResult.SUCCESS, stack);
		}
	}
}