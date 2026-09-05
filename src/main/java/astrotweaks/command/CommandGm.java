
package astrotweaks.command;

import net.minecraft.util.math.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.ICommand;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.List;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;

import astrotweaks.procedure.ProcedureCommandGM;

public class CommandGm {
	public static class CommandHandler implements ICommand {
		@Override
		public int compareTo(ICommand c) {
			return getName().compareTo(c.getName());
		}

		@Override
		public boolean checkPermission(MinecraftServer server, ICommandSender var1) {
			return true;
		}

		@Override
		public List getAliases() {
			return new ArrayList();
		}

		@Override
		public List getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
			return new ArrayList();
		}

		@Override
		public boolean isUsernameIndex(String[] string, int index) {
			return true;
		}

		@Override
		public String getName() {
			return "gm";
		}

		@Override
		public String getUsage(ICommandSender var1) {
			return "/gm [<arguments>]";
		}

		@Override
		public void execute(MinecraftServer server, ICommandSender sender, String[] cmd) {
			if (!(sender.getCommandSenderEntity() instanceof EntityPlayer)) {
			    sender.sendMessage(new TextComponentTranslation("command.only_for_players"));
			    return;
			}
			if (!sender.canUseCommand(2, getName())) {
		        sender.sendMessage(new TextComponentTranslation("command.no_permissions"));
		        return;
		    }
			Entity entity = sender.getCommandSenderEntity();
			if (entity != null) {
				HashMap<String, String> cmdparams = new HashMap<>();
				int[] index = {0};
				Arrays.stream(cmd).forEach(param -> {
					cmdparams.put(Integer.toString(index[0]), param);
					index[0]++;
				});

				ProcedureCommandGM.executeProcedure(entity, cmdparams);
			}
		}
	}
}
