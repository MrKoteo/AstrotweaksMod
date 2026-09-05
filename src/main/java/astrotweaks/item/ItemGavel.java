package astrotweaks.item;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.block.state.IBlockState;

import java.util.List;

import com.google.common.collect.Multimap;
import astrotweaks.procedure.ProcedureGavelRightClickedOnBlock;
import astrotweaks.creativetab.ATCreativeTabs;

public final class ItemGavel {
	public static final Item GAVEL = new ItemGavel.ItemCustom().setRegistryName("astrotweaks", "gavel").setUnlocalizedName("gavel");
	private ItemGavel() {}
	public static class ItemCustom extends Item {
		public ItemCustom() {
			setMaxDamage(100);
			maxStackSize = 1;
			setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
			setContainerItem(this);
		}
		@Override
		public int getMaxItemUseDuration(ItemStack itemstack) {
			return 50;
		}
		@Override
		public float getDestroySpeed(ItemStack par1ItemStack, IBlockState par2Block) {
			return 2F;
		}

		@Override
		public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot slot) {
			Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(slot);
			if (slot == EntityEquipmentSlot.MAINHAND) {
				multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Item modifier", (double) 1, 0));
				multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Item modifier", -2.4, 0));
			}
			return multimap;
		}

		@Override
		public void addInformation(ItemStack itemstack, World world, List<String> tooltip, ITooltipFlag flag) {
  			super.addInformation(itemstack, world, tooltip, flag);
    		tooltip.add(TextFormatting.AQUA + new TextComponentTranslation("item.gavel.tooltip").getFormattedText());
    		//tooltip.add(TextFormatting.AQUA + new TextComponentTranslation("item.plant_fiber.tooltip").getFormattedText());
		}

		@Override
		public EnumActionResult onItemUseFirst(EntityPlayer entity, World world, BlockPos pos, EnumFacing direction, float hitX, float hitY, float hitZ, EnumHand hand) {
			EnumActionResult retval = super.onItemUseFirst(entity, world, pos, direction, hitX, hitY, hitZ, hand);
			ItemStack itemstack = entity.getHeldItem(hand);
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();

			ProcedureGavelRightClickedOnBlock.executeProcedure(x, y, z, world, entity, itemstack);

			return retval;
		}
	}
}
