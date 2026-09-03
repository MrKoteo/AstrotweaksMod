package astrotweaks.procedure;

import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;

import java.util.HashMap;

import astrotweaks.item.ItemXpBoxU;



public class ProcedureATCP {
	public ProcedureATCP() {}

	public static void executeProcedure(Entity entity, HashMap<String, String> params) {
		if (entity == null || params == null) {
		    System.err.println("Failed to load dependencies!");
		    return;
		}

		int xpb_count = 0;
		try { xpb_count = Integer.parseInt(params.getOrDefault("1", "0").trim()); } catch (Exception ignored) {}
		String p2 = params.getOrDefault("2", "");
		boolean xpb_mode = "True".equals(p2) || "1".equals(p2) || "true".equals(p2);

		ItemStack current = new ItemStack(ItemXpBoxU.block, 1);
		NBTTagCompound tag = current.hasTagCompound() ? current.getTagCompound() : new NBTTagCompound();
		tag.setDouble("xp", xpb_count);
		tag.setBoolean("levels", xpb_mode);
		current.setTagCompound(tag);

		if (entity instanceof EntityPlayer) {
			ItemStack stack = current.copy();
			stack.setCount(1);
			ItemHandlerHelper.giveItemToPlayer((EntityPlayer) entity, stack);
		}
	}
}
