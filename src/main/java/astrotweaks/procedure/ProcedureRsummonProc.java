package astrotweaks.procedure;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.entity.EntityList;
import net.minecraft.util.text.translation.I18n;


//import java.util.Map;
import java.util.Random;



public final class ProcedureRsummonProc {
    public ProcedureRsummonProc() {}

    public static void executeProcedure(double x, double y, double z, World world, Entity entity, String entityId, int count, String nbtStr) {
        // Extract dependencies
        //double x = dependencies.get("x") instanceof Number ? ((Number) dependencies.get("x")).doubleValue() : 0;
        //double y = dependencies.get("y") instanceof Number ? ((Number) dependencies.get("y")).doubleValue() : 0;
        //double z = dependencies.get("z") instanceof Number ? ((Number) dependencies.get("z")).doubleValue() : 0;
        //World world = (World) dependencies.get("world");
        //String entityId = (String) dependencies.get("entity_id");
        //int count = dependencies.get("count") instanceof Number ? ((Number) dependencies.get("count")).intValue() : 1;
        //String nbtStr = (String) dependencies.get("nbt");
        if (nbtStr == null) nbtStr = "";

        EntityPlayer player = null;
        if (entity instanceof EntityPlayer) {
            player = (EntityPlayer) entity;
        }
        // Only execute on server side
        if (world.isRemote) return;

        // Validate entity ID
        if (entityId == null || entityId.isEmpty()) {
            sendMessage(player, "Usage: /rsummon <entity_id> [x y z] [count] {nbt}", true);
            return;
        }

        // Clamp count
        if (count < 1) count = 1;
        if (count > 99) {
            sendMessage(player, I18n.translateToLocal("command.rsummon.limit") + "99.", true);
            return;
        }

        // Parse NBT if provided
        NBTTagCompound nbt = null;
        if (!nbtStr.isEmpty()) {
            try {
                nbt = JsonToNBT.getTagFromJson(nbtStr);
            } catch (Exception e) {
                sendMessage(player, I18n.translateToLocal("command.invalid_nbt") + e.getMessage(), true);
                return;
            }
        }

        // Random for offsets and angles
        Random rand = world.rand;

        // Spawn entities
        int spawned = 0;
        for (int i = 0; i < count; i++) {
            // Create entity
            Entity entityToSpawn = EntityList.createEntityByIDFromName(new ResourceLocation(entityId), world);
            if (entityToSpawn == null) {
                sendMessage(player, I18n.translateToLocal("command.invalid_entityid") + entityId, true);
                return; // Stop if invalid
            }

            // Apply NBT first (if any), so we can override position/angles later
            if (nbt != null) {
                entityToSpawn.readFromNBT(nbt);
            }

            // Set position and angles (override if NBT had them)
            double spawnX = x;
            double spawnY = y;
            double spawnZ = z;

            // Optional: Small random offset for multiple entities to prevent stacking
            // Comment out if you want exact same position for all
            if (count > 1) {
                spawnX += (rand.nextFloat() - 0.5) * 0.5; // -0.25 to +0.25
                spawnZ += (rand.nextFloat() - 0.5) * 0.5;
            }

            entityToSpawn.setLocationAndAngles(
                spawnX,
                spawnY,
                spawnZ,
                rand.nextFloat() * 360F,
                0.0F
            );
            // Spawn
            if (world.spawnEntity(entityToSpawn)) {
                spawned++;
            }
        }

        // Send success message
        sendMessage(player, "Spawned " + spawned + " " + entityId + (spawned == 1 ? " entity" : " entities") + "!", false);
    }

    private static void sendMessage(EntityPlayer player, String message, boolean isError) {
    	TextComponentString text = new TextComponentString(message);
    	if (isError) {
	        text.getStyle().setColor(TextFormatting.RED);  // RED color
	    } else {
	        text.getStyle().setColor(TextFormatting.WHITE);  // White for cusses
	    }
        if (player != null) {
	        player.sendMessage(text);
	    } else {
            // Log to server console
            System.out.println("[RSummon] " + message);
        }
    }
}