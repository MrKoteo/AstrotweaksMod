package astrotweaks.Multiverse;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;

/**
 * Registers the dimension ids used by the multiverse (three per level plus the
 * shared global dimension 9999) and their DimensionManager mappings. Run on BOTH
 * sides:
 * <ul>
 *   <li>server &rarr; before creating the WorldServer (WorldServer ctor internally calls
 *       DimensionManager.createProviderFor)</li>
 *   <li>client &rarr; before the respawn packet is handled (WorldClient reads the
 *       provider class straight from DimensionType.getById(dim))</li>
 * </ul>
 *
 * <p>Dimensions are registered with keepLoaded=false so DimensionManager is allowed
 * to unload the backing world once the last player leaves (isMultiverse worlds are
 * unloaded by {@link LevelManager}).</p>
 */
public final class MultiverseDims {

    /** Shared "global" dimension: one overworld-like world over all saves. */
    public static final int GLOBAL_DIM = 9999;

    private MultiverseDims() {
    }

    /** Registers overworld (base), nether (base+1) and end (base+2) of the level. Idempotent. */
    public static void registerLevelDimensions(int baseId) {
        registerOne(baseId, MultiverseWorldProviders.MultiverseOverworld.class);
        registerOne(baseId + 1, MultiverseWorldProviders.MultiverseHell.class);
        registerOne(baseId + 2, MultiverseWorldProviders.MultiverseEnd.class);
    }

    /** Registers the shared global dimension (9999). Idempotent. */
    public static void registerGlobalDimension() {
        registerOne(GLOBAL_DIM, MultiverseWorldProviders.MultiverseGlobal.class);
    }

    private static void registerOne(int dimId, Class<? extends WorldProvider> providerClass) {
        if (DimensionManager.isDimensionRegistered(dimId)) {
            return;
        }
        // EnumHelper.addEnum appends a real constant, Forge's DimensionType.getById
        // will then find the id on both sides. The enum constant name must be unique
        // per id and valid as a Java identifier.
        DimensionType type = DimensionType.register(
                "MV_DIM_" + dimId,
                "_mv",
                dimId,
                providerClass,
                false
        );
        DimensionManager.registerDimension(dimId, type);
    }
}