package astrotweaks.Multiverse;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldProviderSurface;

/**
 * World providers for every multiverse dimension.
 *
 * <p>Instances are created reflectively by Forge's DimensionManager, so the
 * classes MUST be static and have an empty constructor. The dimension id of a
 * provider is inflicted afterwards (setDimension), so we resolve the matching
 * DimensionType by id instead of hardcoding one.</p>
 *
 * <p>Note: we intentionally do NOT override createWorld(...) - that hook does not
 * exist in 1.12.2. Worlds are constructed and registered manually by
 * {@link LevelManager}. The dimension id to save-folder layout is handled by
 * {@link LevelSaveHandler} (an AnvilSaveHandler) together with the instanceof
 * checks inside AnvilSaveHandler.getChunkLoader.</p>
 */
public class MultiverseWorldProviders {

    public static class MultiverseOverworld extends WorldProviderSurface {
        @Override
        public DimensionType getDimensionType() {
            return DimensionType.getById(this.getDimension());
        }

        @Override
        public int getRespawnDimension(EntityPlayerMP player) {
            return this.getDimension();
        }
    }

    public static class MultiverseHell extends WorldProviderHell {
        @Override
        public DimensionType getDimensionType() {
            return DimensionType.getById(this.getDimension());
        }

        @Override
        public int getRespawnDimension(EntityPlayerMP player) {
            return this.getDimension();
        }
    }

    public static class MultiverseEnd extends WorldProviderEnd {
        @Override
        public DimensionType getDimensionType() {
            return DimensionType.getById(this.getDimension());
        }

        @Override
        public int getRespawnDimension(EntityPlayerMP player) {
            return this.getDimension();
        }
    }

    /** The shared global dimension 9999: a regular overworld-like world over all saves. */
    public static class MultiverseGlobal extends WorldProviderSurface {
        @Override
        public DimensionType getDimensionType() {
            return DimensionType.getById(this.getDimension());
        }

        @Override
        public int getRespawnDimension(EntityPlayerMP player) {
            return this.getDimension();
        }
    }
}