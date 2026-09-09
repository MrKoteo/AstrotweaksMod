package astrotweaks.gameplay;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class NoDamageShaking {

    private final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        if (mc.player != null && mc.player.hurtTime > 0) {
            mc.player.hurtTime = 0;
        }
    }
}
