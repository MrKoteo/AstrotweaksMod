package astrotweaks.procedure;

import net.minecraft.potion.PotionEffect;
import net.minecraft.init.MobEffects;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;



public class ProcedureSpoiledFoodEaten {
	public ProcedureSpoiledFoodEaten() {}

	public static void exect(Entity entity) {
		if (entity == null || !(entity instanceof EntityLivingBase)) return;
		double RandInt = 0;
		RandInt = (double) Math.random();


		if (((RandInt) < 0.98)) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.HUNGER, (int) 1800, (int) 1, (false), (false)));
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, (int) 1200, (int) 0, (false), (false)));
		}
		if (((RandInt) < 0.5)) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.HUNGER, (int) 1800, (int) 2, (false), (false)));
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.POISON, (int) 600, (int) 1, (false), (false)));
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, (int) 400, (int) 1, (false), (false)));
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, (int) 400, (int) 0, (false), (false)));
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, (int) 2400, (int) 0, (false), (false)));
		}
		if (((RandInt) < 0.25)) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.HUNGER, (int) 1200, (int) 3, (false), (false)));
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, (int) 400, (int) 1, (false), (false)));
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, (int) 2400, (int) 1, (false), (false)));
		}
		if (((RandInt) < 0.1)) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.HUNGER, (int) 1200, (int) 4, (false), (false)));
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, (int) 2400, (int) 2, (false), (false)));
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, (int) 1200, (int) 1, (false), (false)));
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.POISON, (int) 1200, (int) 2, (false), (false)));
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.UNLUCK, (int) 12000, (int) 0, (false), (false)));
		}
	}
}
