package astrotweaks.Multiverse;

import astrotweaks.AstrotweaksMod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameType;
import net.minecraft.world.ServerWorldEventHandler;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Manages multiverse levels: the name&rarr;iteration registry persisted under the
 * current save's MULTIVERSE/registry.dat, dimension registration and WorldServer
 * creation, player locate persistence and dimension unloading.
 *
 * <p>Every save folder (the overworld that is currently loaded) owns its own
 * MULTIVERSE folder + registry + per-dimension level folders. The registry is
 * additionally bound to the save by a marker file (universe.dat). The shared
 * global dimension (9999) lives in MULTIVERSE_GLOBAL at the server root, outside
 * any single save.</p>
 *
 * <p>A level occupies 3 consecutive dimension ids (base, base+1, base+2) and is saved
 * into its own folder. Worlds are constructed manually (the WorldServer constructor
 * registers them in DimensionManager via setWorld) because Forge's initDimension only
 * knows how to build a WorldServerMulti sharing the main overworld's save handler.</p>
 */
public class LevelManager {

    private static final int BASE_START = 1000;
    private static final int STEP = 100;
    private static final int BASE_MAX = 1000000;

    private static LevelManager INSTANCE;

    private final Map<String, LevelData> levels = new HashMap<>();
    private final Map<Integer, LevelData> dimensionToLevel = new HashMap<>();
    private final Map<UUID, PlayerEntry> playerEntries = new HashMap<>();

    private MinecraftServer cachedServer;
    private File multiverseFolder;
    private File globalFolder;
    private boolean registryLoaded;

    private LevelManager() {
    }

    public static LevelManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LevelManager();
        }
        return INSTANCE;
    }

    // ------------------------------------------------------------------ lifecycle

    /**
     * (Re)initializes the manager when the save's overworld (dim 0) finishes loading.
     * Called from {@code WorldEvent.Load}.
     */
    public void onWorldLoaded(MinecraftServer server) {
        WorldServer world0 = server.getWorld(0);
        if (world0 == null) {
            return;
        }
        File mvFolder = new File(world0.getSaveHandler().getWorldDirectory(), "MULTIVERSE");
        if (isSameFolder(multiverseFolder, mvFolder)) {
            registerGlobalDimensionIfMissing();
            return;
        }
        if (multiverseFolder != null) {
            saveAll(cachedServer != null ? cachedServer : server);
        }
        cachedServer = server;
        multiverseFolder = mvFolder;
        globalFolder = server.getFile("MULTIVERSE_GLOBAL");
        levels.clear();
        dimensionToLevel.clear();
        playerEntries.clear();
        registryLoaded = false;

        migrateLegacyRootFolder(server);

        if (!multiverseFolder.exists() && !multiverseFolder.mkdirs()) {
            throw new IllegalStateException("Cannot create MULTIVERSE folder: " + multiverseFolder);
        }
        if (!globalFolder.exists() && !globalFolder.mkdirs()) {
            throw new IllegalStateException("Cannot create MULTIVERSE_GLOBAL folder: " + globalFolder);
        }

        ensureRegistry(server);
        writeBindingMarker(world0);
        registerGlobalDimensionIfMissing();
        loadPlayerData();
    }

    private void ensureActive(MinecraftServer server) {
        if (multiverseFolder == null) {
            onWorldLoaded(server);
        }
    }

    private void registerGlobalDimensionIfMissing() {
        if (!DimensionManager.isDimensionRegistered(MultiverseDims.GLOBAL_DIM)) {
            MultiverseDims.registerGlobalDimension();
        }
    }

    /**
     * Old builds kept MULTIVERSE at the server root; if the current save has never
     * seen one but a legacy root folder with a registry exists, move it in.
     */
    private void migrateLegacyRootFolder(MinecraftServer server) {
        File legacy = server.getFile("MULTIVERSE");
        if (!multiverseFolder.exists() && legacy.isDirectory() && new File(legacy, "registry.dat").isFile()) {
            if (legacy.renameTo(multiverseFolder)) {
                System.out.println("[Multiverse] Migrated legacy MULTIVERSE folder to " + multiverseFolder);
            } else {
                System.err.println("[Multiverse] Could not migrate legacy MULTIVERSE folder " + legacy);
            }
        }
    }

    private void writeBindingMarker(WorldServer world0) {
        NBTTagCompound root = new NBTTagCompound();
        root.setString("worldName", world0.getSaveHandler().getWorldDirectory().getName());
        root.setLong("worldSeed", world0.getWorldInfo().getSeed());
        writeNbt(new File(multiverseFolder, "universe.dat"), root);
    }

    private static boolean isSameFolder(File a, File b) {
        if (a == null || b == null) {
            return false;
        }
        try {
            return a.getCanonicalPath().equals(b.getCanonicalPath());
        } catch (IOException e) {
            return a.equals(b);
        }
    }

    // ------------------------------------------------------------------ registry

    private void ensureRegistry(MinecraftServer server) {
        if (registryLoaded) {
            return;
        }
        registryLoaded = true;

        File reg = new File(multiverseFolder, "registry.dat");
        if (!reg.isFile()) {
            return;
        }

        NBTTagCompound root = readNbt(reg);
        if (root == null) {
            return;
        }
        NBTTagList list = root.getTagList("levels", 10);
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound entry = list.getCompoundTagAt(i);
            String name = entry.getString("name");
            int baseId = entry.getInteger("baseId");
            long seed = entry.getLong("seed");
            File folder = new File(multiverseFolder, name);

            LevelData data = new LevelData(name, baseId, seed, folder);
            levels.put(name, data);
            linkDimensions(data);
        }
    }

    private void saveRegistry(MinecraftServer server) {
        if (multiverseFolder == null) {
            return;
        }
        NBTTagCompound root = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        for (LevelData data : levels.values()) {
            NBTTagCompound entry = new NBTTagCompound();
            entry.setString("name", data.name);
            entry.setInteger("baseId", data.baseId);
            entry.setLong("seed", data.seed);
            list.appendTag(entry);
        }
        root.setTag("levels", list);
        writeNbt(new File(multiverseFolder, "registry.dat"), root);
    }

    private void linkDimensions(LevelData data) {
        for (int k = 0; k < 3; k++) {
            dimensionToLevel.put(data.baseId + k, data);
        }
    }

    // ------------------------------------------------------------------ levels

    /**
     * Returns the level, creating it when unknown. Existing folders whose registry entry
     * is missing are re-attached with a fresh base id and their stored seed.
     */
    public LevelData getOrCreateLevel(MinecraftServer server, String name, long seed) {
        ensureActive(server);
        if (name == null || name.isEmpty() || name.contains("/") || name.contains("\\") || name.contains("..")) {
            return null;
        }

        LevelData data = levels.get(name);
        if (data != null) {
            MultiverseDims.registerLevelDimensions(data.baseId);
            return data;
        }

        File folder = new File(multiverseFolder, name);
        if (folder.isDirectory() && isLevelDataPresent(folder)) {
            long storedSeed = seed;
            WorldInfo info = new LevelSaveHandler(folder).loadWorldInfo();
            if (info != null) {
                storedSeed = info.getSeed();
            }
            data = registerNewLevel(server, name, storedSeed, folder);
        } else {
            if ((folder.exists() || folder.mkdirs()) && folder.isDirectory()) {
                data = registerNewLevel(server, name, seed, folder);
            } else {
                System.err.println("[Multiverse] Cannot create level folder: " + folder);
            }
        }

        if (data != null) {
            saveRegistry(server);
            MultiverseDims.registerLevelDimensions(data.baseId);
        }
        return data;
    }

    private boolean isLevelDataPresent(File folder) {
        return new File(folder, "level.dat").isFile();
    }

    private LevelData registerNewLevel(MinecraftServer server, String name, long seed, File folder) {
        int baseId = findFreeBaseId();
        LevelData data = new LevelData(name, baseId, seed, folder);
        levels.put(name, data);
        linkDimensions(data);
        return data;
    }

    private int findFreeBaseId() {
        int base = BASE_START;
        while (base < BASE_MAX && isBaseIdUsed(base)) {
            base += STEP;
        }
        if (base >= BASE_MAX) {
            throw new IllegalStateException("No free multiverse dimension range left");
        }
        return base;
    }

    private boolean isBaseIdUsed(int base) {
        for (int k = 0; k < 3; k++) {
            if (DimensionManager.isDimensionRegistered(base + k)) {
                return true;
            }
        }
        return false;
    }

    public LevelData getLevelByName(String name) {
        return levels.get(name);
    }

    public LevelData getLevelByDimensionId(int id) {
        return dimensionToLevel.get(id);
    }

    public boolean isMultiverseDimension(int id) {
        return id == MultiverseDims.GLOBAL_DIM || dimensionToLevel.containsKey(id);
    }

    // ------------------------------------------------------------------ worlds

    /**
     * Loads (or creates) the WorldServer backing the given dimension of the level.
     * This is the 1.12.2 equivalent of a per-save dimension root.
     */
    public WorldServer getOrCreateWorld(MinecraftServer server, LevelData data, LevelDimensionType type) {
        int dimId = data.dimensionId(type);

        WorldServer existing = DimensionManager.getWorld(dimId);
        if (existing != null) {
            return existing;
        }

        // Dimension must be registered before constructing the WorldServer.
        MultiverseDims.registerLevelDimensions(data.baseId);

        return constructWorld(server, data.folder, dimId, data.name, type == LevelDimensionType.END);
    }

    /** Loads (or creates) the shared global dimension world. */
    public WorldServer getOrCreateGlobalWorld(MinecraftServer server) {
        ensureActive(server);
        WorldServer existing = DimensionManager.getWorld(MultiverseDims.GLOBAL_DIM);
        if (existing != null) {
            return existing;
        }
        MultiverseDims.registerGlobalDimension();
        return constructWorld(server, globalFolder, MultiverseDims.GLOBAL_DIM, "__global", false);
    }

    private WorldServer constructWorld(MinecraftServer server, File folder, int dimId, String saveName, boolean buildEndPortal) {
        LevelSaveHandler saveHandler = new LevelSaveHandler(folder);
        WorldInfo info = saveHandler.loadWorldInfo();
        boolean fresh = info == null;
        if (fresh) {
            info = new WorldInfo(
                    new WorldSettings(new Random().nextLong(), GameType.SURVIVAL, true, false, WorldType.DEFAULT),
                    saveName
            );
        }

        WorldServer world = new WorldServer(server, saveHandler, info, dimId, server.profiler);
        world.init();
        world.addEventListener(new ServerWorldEventHandler(server, world));

        if (fresh) {
            // Give a brand-new level the same treatment as a new vanilla world.
            BlockPos spawn = world.getTopSolidOrLiquidBlock(new BlockPos(8, 0, 8));
            world.getWorldInfo().setSpawn(spawn);
            world.getWorldInfo().setServerInitialized(true);
            world.setSpawnPoint(spawn);
            if (buildEndPortal) {
                buildEndExitPortal(world);
            }
        }

        MinecraftForge.EVENT_BUS.post(new WorldEvent.Load(world));

        // Pull a few spawn chunks so the teleported player does not fall through air.
        for (int cx = -1; cx <= 1; cx++) {
            for (int cz = -1; cz <= 1; cz++) {
                world.getChunkFromChunkCoords(cx, cz);
            }
        }

        return world;
    }

    /** Mirrors the vanilla end exit at (100,49,0) so a fresh MV end is never a dead end. */
    private void buildEndExitPortal(WorldServer world) {
        BlockPos base = new BlockPos(100, 49, 0);
        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {
                world.setBlockState(base.add(dx, 0, dz), Blocks.OBSIDIAN.getDefaultState());
            }
        }
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                world.setBlockState(base.add(dx, 1, dz), Blocks.END_PORTAL.getDefaultState());
            }
        }
    }

    // ------------------------------------------------------------------ player persistence

    private File playerDataFile() {
        return new File(multiverseFolder, "mv_playerdata.dat");
    }

    public void recordPlayer(EntityPlayerMP player) {
        playerEntries.put(player.getUniqueID(), new PlayerEntry(player.dimension, player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch));
        savePlayerData();
    }

    public void clearPlayer(UUID uuid) {
        if (playerEntries.remove(uuid) != null) {
            savePlayerData();
        }
    }

    public PlayerEntry getPlayerEntry(UUID uuid) {
        return playerEntries.get(uuid);
    }

    /** Teleports a freshly logged-in player back into their multiverse dimension. */
    public boolean restorePlayer(EntityPlayerMP player) {
        PlayerEntry entry = playerEntries.get(player.getUniqueID());
        if (entry == null) {
            return false;
        }
        MinecraftServer server = player.world.getMinecraftServer();
        if (server == null) {
            return false;
        }
        ensureActive(server);

        LevelData data = dimensionToLevel.get(entry.dimension);
        if (data == null && entry.dimension != MultiverseDims.GLOBAL_DIM) {
            System.err.println("[Multiverse] Dropping unknown restore dimension " + entry.dimension + " for " + player.getName());
            clearPlayer(player.getUniqueID());
            return false;
        }

        WorldServer target;
        if (data != null) {
            LevelDimensionType type = data.typeOf(entry.dimension);
            if (type == null) {
                clearPlayer(player.getUniqueID());
                return false;
            }
            MultiverseDims.registerLevelDimensions(data.baseId);
            target = getOrCreateWorld(server, data, type);
            AstrotweaksMod.PACKET_HANDLER.sendTo(new MessageMultiverse(data.baseId), player);
        } else {
            MultiverseDims.registerGlobalDimension();
            target = getOrCreateGlobalWorld(server);
            AstrotweaksMod.PACKET_HANDLER.sendTo(MessageMultiverse.forGlobal(), player);
        }

        if (target == null) {
            clearPlayer(player.getUniqueID());
            return false;
        }

        player.changeDimension(entry.dimension, new MultiverseTeleporter(new BlockPos((int) entry.x, (int) entry.y, (int) entry.z)));
        player.fallDistance = 0.0F;
        player.connection.setPlayerLocation(entry.x, entry.y + 1.0D, entry.z, entry.yaw, entry.pitch);
        return true;
    }

    private void savePlayerData() {
        if (multiverseFolder == null) {
            return;
        }
        NBTTagCompound root = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        for (Map.Entry<UUID, PlayerEntry> e : playerEntries.entrySet()) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("uuid", e.getKey().toString());
            tag.setInteger("dim", e.getValue().dimension);
            tag.setDouble("x", e.getValue().x);
            tag.setDouble("y", e.getValue().y);
            tag.setDouble("z", e.getValue().z);
            tag.setFloat("yaw", e.getValue().yaw);
            tag.setFloat("pitch", e.getValue().pitch);
            list.appendTag(tag);
        }
        root.setTag("players", list);
        writeNbt(playerDataFile(), root);
    }

    private void loadPlayerData() {
        playerEntries.clear();
        File file = playerDataFile();
        if (!file.isFile()) {
            return;
        }
        NBTTagCompound root = readNbt(file);
        if (root == null) {
            return;
        }
        NBTTagList list = root.getTagList("players", 10);
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound tag = list.getCompoundTagAt(i);
            try {
                UUID uuid = UUID.fromString(tag.getString("uuid"));
                PlayerEntry entry = new PlayerEntry(
                        tag.getInteger("dim"),
                        tag.getDouble("x"), tag.getDouble("y"), tag.getDouble("z"),
                        tag.getFloat("yaw"), tag.getFloat("pitch"));
                playerEntries.put(uuid, entry);
            } catch (IllegalArgumentException e) {
                System.err.println("[Multiverse] Skipping bad player entry: " + e);
            }
        }
    }

    // ------------------------------------------------------------------ unloading

    /** Unloads level and global worlds that no longer contain any (non-exempt) player. */
    public void unloadEmptyDimensions(MinecraftServer server, UUID... ignore) {
        if (multiverseFolder == null) {
            return;
        }
        for (LevelData data : levels.values()) {
            for (int k = 0; k < 3; k++) {
                unloadIfEmpty(data.baseId + k, ignore);
            }
        }
        unloadIfEmpty(MultiverseDims.GLOBAL_DIM, ignore);
    }

    private void unloadIfEmpty(int dim, UUID... ignore) {
        WorldServer world = DimensionManager.getWorld(dim);
        if (world == null) {
            return;
        }
        if (world.playerEntities.isEmpty() || onlyIgnoredPlayers(world.playerEntities, ignore)) {
            DimensionManager.unloadWorld(dim);
        }
    }

    private static boolean onlyIgnoredPlayers(List<EntityPlayer> players, UUID... ignore) {
        for (EntityPlayer player : players) {
            boolean matches = false;
            for (UUID uuid : ignore) {
                if (player.getUniqueID().equals(uuid)) {
                    matches = true;
                    break;
                }
            }
            if (!matches) {
                return false;
            }
        }
        return true;
    }

    // ------------------------------------------------------------------ saving

    /** Saves the registry, player data and all loaded multiverse worlds (server stop). */
    public void saveAll(MinecraftServer server) {
        if (multiverseFolder == null) {
            return;
        }
        saveRegistry(server);
        savePlayerData();
        for (LevelData data : levels.values()) {
            for (int k = 0; k < 3; k++) {
                saveWorldIfLoaded(data.baseId + k);
            }
        }
        saveWorldIfLoaded(MultiverseDims.GLOBAL_DIM);
    }

    private void saveWorldIfLoaded(int dim) {
        WorldServer world = DimensionManager.getWorld(dim);
        if (world == null) {
            return;
        }
        try {
            world.saveAllChunks(true, null);
        } catch (Exception e) {
            System.err.println("[Multiverse] Failed to save world " + dim + ": " + e);
        }
    }

    // ------------------------------------------------------------------ nbt helpers

    private static void writeNbt(File file, NBTTagCompound tag) {
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try (FileOutputStream fout = new FileOutputStream(file)) {
            CompressedStreamTools.writeCompressed(tag, fout);
        } catch (IOException e) {
            System.err.println("[Multiverse] Failed to write " + file + ": " + e);
        }
    }

    private static NBTTagCompound readNbt(File file) {
        try (FileInputStream fin = new FileInputStream(file)) {
            return CompressedStreamTools.readCompressed(fin);
        } catch (IOException e) {
            System.err.println("[Multiverse] Failed to read " + file + ": " + e);
            return null;
        }
    }

    /** Where a player stands inside a multiverse dimension when they leave/download. */
    public static class PlayerEntry {
        public final int dimension;
        public final double x, y, z;
        public final float yaw, pitch;

        PlayerEntry(int dimension, double x, double y, double z, float yaw, float pitch) {
            this.dimension = dimension;
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
        }
    }
}