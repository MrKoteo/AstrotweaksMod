package astrotweaks.procedure;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.command.ICommandSender;

import java.util.Map;

import astrotweaks.AstrotweaksModVariables;


public class ProcedureAstroTechCP {
	public ProcedureAstroTechCP() {}

	public static void executeProcedure(Entity sender, Map<String, Object> cmdparams) {
		//Map<String, Object> cmdparams = (Map<String, Object>) cmdparamsObj;

		if (!AstrotweaksModVariables.AstroTech_Environment) {
			sendLocalized(sender, "No modules");
			return;
		}

		String arg0 = getParam(cmdparams, 0);
		if (!"info".equalsIgnoreCase(arg0)) {
			sendLocalized(sender, "invalid.argument");
			return;
		}

		String targetName = getParam(cmdparams, 1);
		EntityPlayer targetPlayer = null;
		if (!targetName.isEmpty() && sender.world != null && !sender.world.isRemote) {
			MinecraftServer server = sender.world.getMinecraftServer();
			if (server != null) {
				targetPlayer = server.getPlayerList().getPlayerByUsername(targetName);
			}
		}

		Entity recipientEntity = (targetPlayer != null) ? targetPlayer : sender;

		sendLocalized(recipientEntity, "info.line0");
		sendLocalized(recipientEntity, "info.line1");
		sendLocalized(recipientEntity, "info.line2");
		sendLocalized(recipientEntity, "info.empty");
		sendLocalized(recipientEntity, "info.line3");
		sendLocalized(recipientEntity, "info.line4");
		sendLocalized(recipientEntity, "info.empty");
	}

	private static String getParam(Map<String, Object> params, int idx) {
		Object v = params.get(Integer.toString(idx));
		return (v instanceof String) ? ((String) v).trim() : "";
	}

	private static void sendLocalized(Entity target, String key, Object... args) {
		if (target == null || key == null) return;
		ITextComponent comp = new TextComponentTranslation(key, args);
		if (target instanceof EntityPlayerMP) {
			((EntityPlayerMP) target).sendMessage(comp);
			return;
		}

		World world = target.world;
		if (world == null || world.isRemote) return;
		MinecraftServer server = world.getMinecraftServer();
		if (server == null) return;
		ICommandSender sender = new ICommandSender() {
			@Override public String getName() { return ""; }
			@Override public boolean canUseCommand(int perm, String cmd) { return true; }
			@Override public World getEntityWorld() { return world; }
			@Override public MinecraftServer getServer() { return server; }
			@Override public boolean sendCommandFeedback() { return false; }
			@Override public BlockPos getPosition() { return target.getPosition(); }
			@Override public Vec3d getPositionVector() { return new Vec3d(target.posX, target.posY, target.posZ); }
			@Override public Entity getCommandSenderEntity() { return target; }
		};

		String json = "[{\"translate\":\"" + escape(key) + "\"}]";
		server.getCommandManager().executeCommand(sender, "tellraw @s " + json);
	}

	private static String escape(String s) {
		return s.replace("\\", "\\\\").replace("\"", "\\\"");
	}
}
