package astrotweaks.Multiverse;

import net.minecraft.world.GameType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.WorldInfo;

import java.io.File;

/** Immutable description of one multiverse level (one "other universe"). */
public class LevelData {

    public final String name;
    public final int baseId;
    public final long seed;
    public final File folder;

    public LevelData(String name, int baseId, long seed, File folder) {
        this.name = name;
        this.baseId = baseId;
        this.seed = seed;
        this.folder = folder;
    }

    /** Global minecraft dimension id for the requested type (baseId, baseId+1, baseId+2). */
    public int dimensionId(LevelDimensionType type) {
        switch (type) {
            case OVERWORLD:
                return baseId;
            case NETHER:
                return baseId + 1;
            case END:
                return baseId + 2;
            default:
                throw new IllegalStateException("Unknown level dimension type: " + type);
        }
    }

    /** Which type the given global dimension id maps to, or null if it is not part of this level. */
    public LevelDimensionType typeOf(int dimensionId) {
        if (dimensionId == baseId) {
            return LevelDimensionType.OVERWORLD;
        }
        if (dimensionId == baseId + 1) {
            return LevelDimensionType.NETHER;
        }
        if (dimensionId == baseId + 2) {
            return LevelDimensionType.END;
        }
        return null;
    }

    /**
     * Reads level.dat from the level folder, or creates a fresh WorldInfo when the
     * level is brand new (seed comes from the command then).
     */
    public WorldInfo loadOrCreateWorldInfo() {
        WorldInfo info = new LevelSaveHandler(folder).loadWorldInfo();
        if (info == null) {
            info = new WorldInfo(
                    new WorldSettings(seed, GameType.SURVIVAL, true, false, WorldType.DEFAULT),
                    name
            );
        }
        return info;
    }
}