package astrotweaks.util;

import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import astrotweaks.ModVariables;

@Mod.EventBusSubscriber(modid = "astrotweaks")
public final class ExpDamageMult {
    //private static final double MULTIPLIER = 1.5D;

    //final double EDM = ModVariables.ExplosionDamageMult;

    private ExpDamageMult() {}

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        if (source != null && source.isExplosion()) {
            float original = event.getAmount();
            float modified = (float) (original * ModVariables.ExplosionDamageMult);
            event.setAmount(modified);
        }
    }
}

