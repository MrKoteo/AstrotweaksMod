package astrotweaks.gameplay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.multiplayer.GuiConnecting;



//@Mod.EventBusSubscriber(modid = "astrotweaks", value = Side.CLIENT)
public final class LetMeDisconnect {

    private static final int DISCONNECT_BUTTON_ID = 44289;


    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onInitGui(GuiScreenEvent.InitGuiEvent.Post event) {
        GuiScreen screen = event.getGui();

        if (!isSupportedScreen(screen)) return;

        int buttonWidth = 150;
        int buttonHeight = 20;
        int x = (screen.width - buttonWidth) / 2;
        int y = screen.height - 40;

        event.getButtonList().add(new GuiButton(DISCONNECT_BUTTON_ID, x, y, buttonWidth, buttonHeight, I18n.format("gui.disconnect")));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onButtonPressed(GuiScreenEvent.ActionPerformedEvent.Pre event) {
        GuiButton button = event.getButton();
        GuiScreen screen = event.getGui();

        if (button == null || button.id != DISCONNECT_BUTTON_ID) return;

        if (!isSupportedScreen(screen)) return;
        
        disconnect();

        // Не даём игре обработать кнопку повторно
        event.setCanceled(true);
    }
    private static boolean isSupportedScreen(GuiScreen screen) {
        return screen instanceof GuiDownloadTerrain || screen instanceof GuiConnecting;
    }
    private static void disconnect() {
        Minecraft minecraft = Minecraft.getMinecraft();

        WorldClient world = minecraft.world;

        if (world != null) world.sendQuittingDisconnectingPacket();
        
        minecraft.loadWorld(null);
        minecraft.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
    }
}
