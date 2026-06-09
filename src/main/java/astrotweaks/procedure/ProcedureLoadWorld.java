package astrotweaks.procedure;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.World;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;

import java.util.Map;

import astrotweaks.ElementsAstrotweaksMod;
import astrotweaks.AstrotweaksModVariables;

@ElementsAstrotweaksMod.ModElement.Tag
public class ProcedureLoadWorld extends ElementsAstrotweaksMod.ModElement {
	public ProcedureLoadWorld(ElementsAstrotweaksMod instance) {
		super(instance, 317);
	}

	// Lightweight ICommandSender implementation that delegates to a world/server
	private static class SimpleCommandSender implements ICommandSender {
		private final World world;
		SimpleCommandSender(World world) { this.world = world; }

		@Override public String getName() { return ""; }
		@Override public boolean canUseCommand(int perm, String cmd) { return true; }
		@Override public World getEntityWorld() { return world; }
		@Override public MinecraftServer getServer() { return world.getMinecraftServer(); }
		@Override public boolean sendCommandFeedback() { return false; }
		@Override public BlockPos getPosition() { return BlockPos.ORIGIN; }
		@Override public Vec3d getPositionVector() { return Vec3d.ZERO; }
		//@Override public Entity getCommandSenderEntity() { return null; } // optional for 1.12
	}

	// Helper: run a command on server if available
	private static void runCommand(World world, String command) {
		if (world == null || world.isRemote) return;
		MinecraftServer server = world.getMinecraftServer();
		if (server == null) return;
		server.getCommandManager().executeCommand(new SimpleCommandSender(world), command);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		Object w = dependencies.get("world");
		if (!(w instanceof World)) {
			System.err.println("Failed to load dependency world for procedure LoadWorld!");
			return;
		}
		World world = (World) w;
		if (!AstrotweaksModVariables.AstroTech_Environment) return;

		// desired commands
		runCommand(world, "scoreboard objectives add deathCountX deathCount \u0421\u043C\u0435\u0440\u0442\u0438");
		runCommand(world, "gamerule randomTickSpeed 2");
	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		World world = event.getWorld();
		java.util.HashMap<String, Object> deps = new java.util.HashMap<>();
		deps.put("world", world);
		deps.put("event", event);
		executeProcedure(deps);
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
	}
}
