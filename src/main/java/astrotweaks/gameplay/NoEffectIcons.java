package astrotweaks.gameplay;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NoEffectIcons {
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onPotionOverlayEvent(RenderGameOverlayEvent.Pre event) {
    if (event.getType() == RenderGameOverlayEvent.ElementType.POTION_ICONS)
        event.setCanceled(true); 
    }
}
