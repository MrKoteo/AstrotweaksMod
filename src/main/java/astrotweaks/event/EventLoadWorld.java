package astrotweaks.event;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.World;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.command.ICommandSender;



import astrotweaks.ElementsAstrotweaksMod;
import astrotweaks.ModVariables;



public class EventLoadWorld {
	public EventLoadWorld() {}

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

	public static void exect(World world, WorldEvent.Load event) {
		//if (!ModVariables.AstroTech_Environment && !ModVariables.Marked) return;



		// desired commands
		runCommand(world, "scoreboard objectives add deathCountX deathCount \u0421\u043C\u0435\u0440\u0442\u0438");
		runCommand(world, "gamerule randomTickSpeed 2");

		ModVariables.MapVariables.get(world).Marked = true;
	}
	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		World world = event.getWorld();
		if (ModVariables.AstroTech_Environment && !ModVariables.MapVariables.get(world).Marked) {exect(world, event); }
	}
}
