package astrotweaks.gameplay;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class StepUp {

    private static final float DEFAULT_STEP_HEIGHT = 0.6F;
    private static final float STEP_HEIGHT = 1.0F;

    private boolean enabled = true;

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        //Minecraft minecraft = Minecraft.getMinecraft();
        EntityPlayer player = event.player;

        if (player == null) {
            return;
        }

        player.stepHeight = enabled ? STEP_HEIGHT : DEFAULT_STEP_HEIGHT;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public boolean isEnabled() {
        return enabled;
    }
}
