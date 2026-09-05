package astrotweaks.command;

import net.minecraft.util.math.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.ICommand;
import net.minecraft.util.text.TextComponentString;

import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Field;

import astrotweaks.ModVariables;

public class CommandATVars {
	public static class CommandHandler implements ICommand {
		@Override
		public int compareTo(ICommand c) {
			return getName().compareTo(c.getName());
		}

		@Override
		public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
			return true;
		}

		@Override
		public List<String> getAliases() {
			return new ArrayList<>();
		}

		@Override
		public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
			return new ArrayList<>();
		}

		@Override
		public boolean isUsernameIndex(String[] args, int index) {
			return false;
		}

		@Override
		public String getName() {
			return "atv";
		}

		@Override
		public String getUsage(ICommandSender sender) {
			return "/atv get <VarName> \n/atv set <VarName> <value>[TypeSuffix]";
		}

		@Override
		public void execute(MinecraftServer server, ICommandSender sender, String[] cmd) {
			if (cmd == null || cmd.length < 2) {
				sender.sendMessage(new TextComponentString("Usage: " + getUsage(sender)));
				return;
			}

			String action = cmd[0].toLowerCase();
			String varName = cmd[1];

			try {
				Field f = ModVariables.class.getDeclaredField(varName);
				f.setAccessible(true);
				Class<?> type = f.getType();

				if ("get".equals(action)) {
					Object val = f.get(null);
					sender.sendMessage(new TextComponentString(varName + " = " + String.valueOf(val)));
					return;
				}

				if (!"set".equals(action)) {
					sender.sendMessage(new TextComponentString("Unknown action: " + action));
					return;
				}

				if (cmd.length < 3) {
					sender.sendMessage(new TextComponentString("Usage: " + getUsage(sender)));
					return;
				}
				StringBuilder sb = new StringBuilder();
				for (int i = 2; i < cmd.length; i++) {
					if (i > 2) sb.append(' ');
					sb.append(cmd[i]);
				}
				String raw = sb.toString().trim();

				boolean quoted = false;
				if (raw.length() >= 2) {
					char first = raw.charAt(0);
					char last = raw.charAt(raw.length() - 1);
					if ((first == '"' && last == '"') || (first == '\'' && last == '\'')) {
						quoted = true;
						raw = raw.substring(1, raw.length() - 1);
					}
				}

				String suffix = "";
				String valueToken = raw;
				if (!quoted && raw.length() > 0) {
					char lastChar = raw.charAt(raw.length() - 1);
					if (Character.isLetter(lastChar)) {
						suffix = String.valueOf(lastChar).toUpperCase();
						valueToken = raw.substring(0, raw.length() - 1);
					}
				}

				// If quoted or explicit S => string
				if (quoted || "S".equals(suffix) || type == String.class) {
					f.set(null, valueToken);
					sender.sendMessage(new TextComponentString("Set " + varName + " = \"" + valueToken + "\""));
					return;
				}

				// If no suffix, infer from field type or from boolean literal (true/false) without suffix
				String effectiveType = suffix;
				if (effectiveType.isEmpty()) {
					// prefer boolean if literal "true"/"false" (case-insensitive)
					String low = valueToken.toLowerCase();
					if ("true".equals(low) || "false".equals(low)) {
						effectiveType = "B";
					} else if (type == int.class || type == Integer.class) effectiveType = "I";
					else if (type == long.class || type == Long.class) effectiveType = "L";
					else if (type == double.class || type == Double.class) effectiveType = "D";
					else if (type == float.class || type == Float.class) effectiveType = "F";
					else if (type == boolean.class || type == Boolean.class) effectiveType = "B";
					else if (type == String.class) effectiveType = "S";
				}

				switch (effectiveType) {
					case "I": {
						int v = Integer.parseInt(valueToken);
						if (type == int.class || type == Integer.class) f.setInt(null, v);
						else f.set(null, Integer.valueOf(v));
						break;
					}
					case "L": {
						long v = Long.parseLong(valueToken);
						if (type == long.class || type == Long.class) f.setLong(null, v);
						else f.set(null, Long.valueOf(v));
						break;
					}
					case "D": {
						double v = Double.parseDouble(valueToken);
						if (type == double.class || type == Double.class) f.setDouble(null, v);
						else f.set(null, Double.valueOf(v));
						break;
					}
					case "F": {
						float v = Float.parseFloat(valueToken);
						if (type == float.class || type == Float.class) f.setFloat(null, v);
						else f.set(null, Float.valueOf(v));
						break;
					}
					case "B": {
						boolean v = Boolean.parseBoolean(valueToken.toLowerCase());
						if (type == boolean.class || type == Boolean.class) f.setBoolean(null, v);
						else f.set(null, Boolean.valueOf(v));
						break;
					}
					default:
						sender.sendMessage(new TextComponentString("Unsupported or ambiguous type for setting variable."));
						return;
				}

				sender.sendMessage(new TextComponentString("Set " + varName + " = " + valueToken));
			} catch (NoSuchFieldException e) {
				sender.sendMessage(new TextComponentString("Variable not found: " + varName));
			} catch (NumberFormatException e) {
				sender.sendMessage(new TextComponentString("Value parsing failed: " + e.getMessage()));
			} catch (IllegalAccessException e) {
				sender.sendMessage(new TextComponentString("Failed to set variable: " + e.getMessage()));
			} catch (Exception e) {
				sender.sendMessage(new TextComponentString("Error: " + e.getClass().getSimpleName() + " " + e.getMessage()));
			}
		}
	}
}
