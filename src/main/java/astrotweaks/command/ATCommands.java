package astrotweaks.command;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class ATCommands {
	public static void init(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandSwitchDim.CommandHandler());
		event.registerServerCommand(new CommandGm.CommandHandler());
		event.registerServerCommand(new CommandRsummon.CommandHandler());
		event.registerServerCommand(new CommandShowDeathsCC.CommandHandler());
		event.registerServerCommand(new CommandATVars.CommandHandler());
		event.registerServerCommand(new CommandATCC.CommandHandler());
		event.registerServerCommand(new CommandAstrotechCC.CommandHandler());
	}
}
