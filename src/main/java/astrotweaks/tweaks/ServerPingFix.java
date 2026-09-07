package astrotweaks.tweaks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import astrotweaks.ModVariables;

@Mod.EventBusSubscriber(modid="astrotweaks",value=Side.CLIENT)
@SideOnly(Side.CLIENT)
public final class ServerPingFix { /* Это то же самое, что и в моде  "PingFix" */
    private static final long REFRESH_INTERVAL_MS = 10_000L;
    private static final long SCREEN_OPEN_REFRESH_MS = 1_000L;

    private static GuiScreen trackedScreen;
    private static long lastRefreshMs;

    private static final boolean EnableSPF = ModVariables.ServerPingFix;

    private ServerPingFix() {}

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!ModVariables.ServerPingFix) return;

    	if (EnableSPF) {
            trackedScreen = null;
            lastRefreshMs = 0L;
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.currentScreen == null) {
            trackedScreen = null;
            return;
        }

        GuiScreen curScreen = mc.currentScreen;
        long now = System.currentTimeMillis();

        // if screen changed
        if (curScreen != trackedScreen) {
            trackedScreen = curScreen;
            if (curScreen instanceof GuiMultiplayer && (now - lastRefreshMs) >= SCREEN_OPEN_REFRESH_MS) {
                refreshMultiplayerScreen(mc);
                lastRefreshMs = now;
            }
            return;
        }

        // if not MP screen - exit
        if (!(curScreen instanceof GuiMultiplayer)) return;

        // check upd interval
        if (now - lastRefreshMs < REFRESH_INTERVAL_MS) return;

        refreshMultiplayerScreen(mc);
        lastRefreshMs = now;
    }
    private static void refreshMultiplayerScreen(Minecraft mc) {
        mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
    }
}
