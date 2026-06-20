package astrotweaks.command;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.Arrays;

import astrotweaks.ElementsAstrotweaksMod;
import astrotweaks.procedure.ProcedureAstroTechCP;

@ElementsAstrotweaksMod.ModElement.Tag
public class CommandAstrotechCC extends ElementsAstrotweaksMod.ModElement {
	public CommandAstrotechCC(ElementsAstrotweaksMod instance) {
		super(instance, 325);
	}

	@Override
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandHandler());
	}

	public static class CommandHandler implements ICommand {
		@Override public String getName() { return "astrotech"; }
		@Override public String getUsage(ICommandSender sender) { return "/astrotech [args]"; }
		@Override public List<String> getAliases() { return Arrays.asList(); }
		@Override public boolean checkPermission(MinecraftServer server, ICommandSender sender) { return true; }
		@Override public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) { return Arrays.asList(); }
		@Override public boolean isUsernameIndex(String[] args, int index) { return index == 1; } // idx 1 - target player
		@Override public int compareTo(ICommand o) { return getName().compareTo(o.getName()); }

		@Override
		public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
			Entity entity = sender.getCommandSenderEntity();
			if (entity == null) return;
			Map<String, String> cmdparams = IntStream.range(0, args.length).boxed().collect(Collectors.toMap(i -> Integer.toString(i), i -> args[i]));

			Map<String, Object> deps = new HashMap<>();
			deps.put("entity", entity);
			deps.put("cmdparams", cmdparams);

			ProcedureAstroTechCP.executeProcedure(deps);
		}
	}
}
