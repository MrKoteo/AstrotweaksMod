package astrotweaks.Multiverse;

import astrotweaks.AstrotweaksMod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Glues the multiverse into the game:
 * <ul>
 *   <li>world bind: on overworld load, (re)attach this save's MULTIVERSE folder</li>
 *   <li>re-login: return players to their last multiverse dimension</li>
 *   <li>portals: nether / end portals inside a level stay inside that level</li>
 *   <li>persistence: remember the position of players inside a level</li>
 *   <li>unloading: drop worlds once their last player leaves</li>
 * </ul>
 */
public class MultiverseEvents {

    private static final Set<UUID> SKIP_PORTAL_REMAP = new HashSet<>();

    private int tickCounter;

    /**
     * Runs a player dimension change without the portal re-mapping (used by
     * /mv join 0, where a nether portal in the level would otherwise hijack
     * the trip to the vanilla overworld).
     */
    public static void teleportIgnoringPortalRemap(EntityPlayerMP player, int dimension, ITeleporter teleporter) {
        SKIP_PORTAL_REMAP.add(player.getUniqueID());
        try {
            player.changeDimension(dimension, teleporter);
        } finally {
            SKIP_PORTAL_REMAP.remove(player.getUniqueID());
        }
    }

    // ------------------------------------------------------------------ world bind

    @SubscribeEvent
    public void onWorldLoaded(WorldEvent.Load event) {
        if (event.getWorld() == null || event.getWorld().isRemote) {
            return;
        }
        if (event.getWorld().provider == null || event.getWorld().provider.getDimension() != 0) {
            return;
        }
        MinecraftServer server = event.getWorld().getMinecraftServer();
        if (server != null) {
            LevelManager.getInstance().onWorldLoaded(server);
        }
    }

    // ------------------------------------------------------------------ re-login

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player == null || event.player.world.isRemote) {
            return;
        }
        if (!(event.player instanceof EntityPlayerMP)) {
            return;
        }
        EntityPlayerMP player = (EntityPlayerMP) event.player;
        LevelManager lm = LevelManager.getInstance();
        if (lm.getPlayerEntry(player.getUniqueID()) != null) {
            lm.restorePlayer(player);
        }
    }

    // ------------------------------------------------------------------ portal remap

    @SubscribeEvent
    public void onTravelToDimension(EntityTravelToDimensionEvent event) {
        Entity entity = event.getEntity();
        if (entity == null || entity.getEntityWorld() == null || entity.getEntityWorld().isRemote) {
            return;
        }

        EntityPlayerMP player = entity instanceof EntityPlayerMP ? (EntityPlayerMP) entity : null;
        if (player != null && SKIP_PORTAL_REMAP.remove(player.getUniqueID())) {
            return;
        }

        int from = entity.dimension;
        int to = event.getDimension();

        LevelManager lm = LevelManager.getInstance();
        LevelData data = lm.getLevelByDimensionId(from);
        if (data == null) {
            return;
        }

        LevelDimensionType targetType = resolvePortalTarget(data.typeOf(from), to);
        if (targetType == null) {
            return;
        }

        MinecraftServer server = entity.getServer();
        if (server == null) {
            return;
        }

        event.setCanceled(true);

        WorldServer targetWorld = lm.getOrCreateWorld(server, data, targetType);
        if (targetWorld == null) {
            return;
        }

        int corrected = data.dimensionId(targetType);

        ITeleporter teleporter;
        if (targetType == LevelDimensionType.END) {
            teleporter = new MultiverseTeleporter(endSpawn(targetWorld));
        } else if (targetType == LevelDimensionType.OVERWORLD && data.typeOf(from) == LevelDimensionType.END) {
            teleporter = new MultiverseTeleporter(targetWorld.getSpawnPoint());
        } else {
            // Overworld <-> nether: keep the vanilla movement-factor scaling and
            // portal pairing (find/create a matching portal in the target world).
            teleporter = targetWorld.getDefaultTeleporter();
        }

        if (player != null) {
            AstrotweaksMod.PACKET_HANDLER.sendTo(new MessageMultiverse(data.baseId), player);
            player.changeDimension(corrected, teleporter);
        } else {
            entity.changeDimension(corrected, teleporter);
        }
    }

    private static BlockPos endSpawn(WorldServer world) {
        BlockPos coordinate = world.getSpawnCoordinate();
        if (coordinate == null || coordinate.equals(BlockPos.ORIGIN)) {
            return world.getSpawnPoint();
        }
        return coordinate;
    }

    /**
     * Where a portal in the level should lead. Everything else is left untouched
     * (vanilla behavior for real-lane travel, and no-op for already-corrected targets).
     */
    private static LevelDimensionType resolvePortalTarget(LevelDimensionType from, int to) {
        if (from == null) {
            return null;
        }
        switch (from) {
            case OVERWORLD:
                if (to == -1) {
                    return LevelDimensionType.NETHER;
                }
                if (to == 1) {
                    return LevelDimensionType.END;
                }
                return null;
            case NETHER:
                if (to == -1 || to == 0) {
                    return LevelDimensionType.OVERWORLD;
                }
                if (to == 1) {
                    return LevelDimensionType.END;
                }
                return null;
            case END:
                if (to == -1) {
                    return LevelDimensionType.NETHER;
                }
                if (to == 0 || to == 1) {
                    return LevelDimensionType.OVERWORLD;
                }
                return null;
            default:
                return null;
        }
    }

    // ------------------------------------------------------------------ persistence

    @SubscribeEvent
    public void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.player == null || event.player.world.isRemote) {
            return;
        }
        if (!(event.player instanceof EntityPlayerMP)) {
            return;
        }
        EntityPlayerMP player = (EntityPlayerMP) event.player;
        LevelManager lm = LevelManager.getInstance();
        int dim = player.dimension;
        if (lm.isMultiverseDimension(dim)) {
            lm.recordPlayer(player);
        } else {
            lm.clearPlayer(player.getUniqueID());
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.player == null || event.player.world.isRemote) {
            return;
        }
        if (!(event.player instanceof EntityPlayerMP)) {
            return;
        }
        EntityPlayerMP player = (EntityPlayerMP) event.player;
        LevelManager lm = LevelManager.getInstance();
        int dim = player.dimension;
        if (lm.isMultiverseDimension(dim)) {
            lm.recordPlayer(player);
        } else {
            lm.clearPlayer(player.getUniqueID());
        }
        MinecraftServer server = player.world.getMinecraftServer();
        if (server != null) {
            lm.unloadEmptyDimensions(server, player.getUniqueID());
        }
    }

    // ------------------------------------------------------------------ unloading / save

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        if (++tickCounter % 100 != 0) {
            return;
        }
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        if (server == null || server.getWorld(0) == null) {
            return;
        }
        LevelManager.getInstance().unloadEmptyDimensions(server);
    }
}