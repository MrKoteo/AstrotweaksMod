package astrotweaks.item;

import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import astrotweaks.creativetab.ATCreativeTabs;

public final class ItemVoidAntimatter {
	public static final Item VOID_ANTIMATTER = new ItemVoidAntimatter.ItemCustom().setRegistryName("astrotweaks", "void_antimatter").setUnlocalizedName("void_antimatter");

	// Timer for limit max execs per sec (thread-safe)
	private static final Map<Integer, Long> lastUseMs = new ConcurrentHashMap<>();
	private ItemVoidAntimatter() {}
	public static class ItemCustom extends Item {
		public ItemCustom() {
			setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
		}
		@Override
		public void addInformation(ItemStack itemstack, World world, List<String> tooltip, ITooltipFlag flag) {
			super.addInformation(itemstack, world, tooltip, flag);
			tooltip.add(TextFormatting.RED + new TextComponentTranslation("item.void_antimatter.tooltip").getFormattedText());
		}
		@Override
		public void onUpdate(ItemStack itemstack, World world, Entity entity, int slot, boolean par5) {
			super.onUpdate(itemstack, world, entity, slot, par5);
			if (world.isRemote) return;

			if (!(entity instanceof EntityLivingBase)) return;
			// Only react for the item instance held in main hand
			EntityLivingBase living = (EntityLivingBase) entity;
			ItemStack main = living.getHeldItemMainhand();
			if (main == null || main == ItemStack.EMPTY) return;
			// Compare item types (not NBT/count)
			if (main.getItem() != ItemVoidAntimatter.VOID_ANTIMATTER) return;

			int id = entity.getEntityId();
			long now = System.currentTimeMillis();
			Long last = lastUseMs.get(id);
			if (last != null && now - last < 500) return; // rate limit: 500 ms
			lastUseMs.put(id, now);

			// Execute explosion / item removal logic
			AMExplode(living, world);
		}

		// Remove held antimatter items and create explosion at entity position (server-side)
		private static void AMExplode(EntityLivingBase entity, World world) {
			boolean mainIsVoid = entity.getHeldItemMainhand() != null && entity.getHeldItemMainhand().getItem() == ItemVoidAntimatter.VOID_ANTIMATTER;
			boolean offIsVoid = entity.getHeldItemOffhand() != null && entity.getHeldItemOffhand().getItem() == ItemVoidAntimatter.VOID_ANTIMATTER;

			// If both hands hold the item -> clear both; else clear main only
			if (mainIsVoid) {
				entity.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
			} else if (offIsVoid) {
				entity.setHeldItem(EnumHand.OFF_HAND, ItemStack.EMPTY);
			} else if (offIsVoid && mainIsVoid) {
				entity.setHeldItem(EnumHand.OFF_HAND, ItemStack.EMPTY);
				entity.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
			} else {
				return;
			}
			// If player, mark inventory dirty so change syncs
			if (entity instanceof EntityPlayerMP) {
				((EntityPlayerMP) entity).inventory.markDirty();
			}
			// Explosion at entity integer coordinates, power 13, causes block damage
			double x = Math.floor(entity.posX);
			double y = Math.floor(entity.posY);
			double z = Math.floor(entity.posZ);



			world.createExplosion(null, x, y, z, 13.4F, true);
			//Explosion explosion1 = new Explosion(world,null,x, y, z,13.3F,false,true);
			//explosion1.doExplosionA();
			//explosion1.doExplosionB(true);

			Explosion explosion2 = new Explosion(world,null,x, y, z,20F,false,false);
			explosion2.doExplosionA();

			if (entity instanceof EntityPlayerMP) {
				entity.attackEntityFrom(
					DamageSource.causeExplosionDamage(explosion2),
					100.0F
				);
			}
		}
	}
}
