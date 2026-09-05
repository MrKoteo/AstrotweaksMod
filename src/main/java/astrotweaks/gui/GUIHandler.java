package astrotweaks.gui;

import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.world.World;


import astrotweaks.tech.mt.MTGUI;
import astrotweaks.tech.ark.ArkGUI;
//import astrotweaks.tech.qts.QTPSupGUI;






@Mod.EventBusSubscriber(modid = "astrotweaks")
public class GUIHandler {


    public static class GuiHandler implements IGuiHandler {
		@Override
		public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
			if (id == MTGUI.GUIID)
				return new MTGUI.GuiContainerMod(world, x, y, z, player);
			if (id == ArkGUI.GUIID)
            	return null;
			//if (id == QTPSupGUI.GUIID)
            //	return null;
			return null;
		}

		@Override
		public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
			if (id == MTGUI.GUIID)
				return new MTGUI.GuiWindow(world, x, y, z, player);
			if (id == ArkGUI.GUIID)
                return new ArkGUI.GuiWindow(world, x, y, z, player);
			//if (id == QTPSupGUI.GUIID)
            //    return new QTPSupGUI.GuiWindow(world, x, y, z, player);
			return null;
		}
	}

    //
}
