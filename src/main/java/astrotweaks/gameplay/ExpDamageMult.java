package astrotweaks.gameplay;

import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import astrotweaks.ModVariables;

@Mod.EventBusSubscriber(modid = "astrotweaks")
public final class ExpDamageMult {
    private ExpDamageMult() {}

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        final double EDM = ModVariables.ExplosionDamageMult;
        if (source != null && source.isExplosion()) {
            float original = event.getAmount();
            float modified = (float) (original * EDM);
            event.setAmount(modified);
        }
    }
}
