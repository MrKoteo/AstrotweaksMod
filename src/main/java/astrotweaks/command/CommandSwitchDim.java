package astrotweaks.command;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import net.minecraft.util.math.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.ICommand;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import astrotweaks.procedure.ProcedureSwitchDimProc;
import astrotweaks.ElementsAstrotweaksMod;

@ElementsAstrotweaksMod.ModElement.Tag
public class CommandSwitchDim extends ElementsAstrotweaksMod.ModElement {
	public CommandSwitchDim(ElementsAstrotweaksMod instance) {
		super(instance, 612);
	}

	@Override
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandHandler());
	}

	public static class CommandHandler implements ICommand {
		@Override
		public int compareTo(ICommand c) {
			return getName().compareTo(c.getName());
		}
		@Override
		public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
			return true;
		}
		@Override public List getAliases() {return new ArrayList();}
		@Override public List getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {return new ArrayList();}
		@Override public boolean isUsernameIndex(String[] args, int index) { return index == 1; }
		@Override public String getName() { return "dim"; }
		@Override
		public String getUsage(ICommandSender sender) {
			return "/dim <dimID> <playerName|@s> [x y z]|<playerName>";
		}

		@Override
		public void execute(MinecraftServer server, ICommandSender sender, String[] cmd) {
			// check perms (level 2 = OP)
			if (!sender.canUseCommand(2, getName())) {
				sender.sendMessage(new TextComponentTranslation("command.no_permissions"));
				return;
			}
			// get source Entity may be null for console)
			Entity sourceEntity = sender.getCommandSenderEntity();
			// cmdparams
			HashMap<String, String> cmdparams = new HashMap<>();
			for (int i = 0; i < cmd.length; i++) {
				cmdparams.put(Integer.toString(i), cmd[i]);
			}
			ProcedureSwitchDimProc.executeProcedure(sourceEntity, cmdparams, false);
		}
	}
}
