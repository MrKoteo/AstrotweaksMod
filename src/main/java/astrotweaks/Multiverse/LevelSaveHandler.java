package astrotweaks.Multiverse;

import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.world.chunk.storage.AnvilSaveHandler;

import java.io.File;

/**
 * Anvil save handler rooted at the level folder (MULTIVERSE/&lt;name&gt;).
 *
 * <p>Like every other save in the game, chunks are laid out as:</p>
 * <ul>
 *     <li>overworld &rarr; &lt;name&gt;/region</li>
 *     <li>nether &rarr; &lt;name&gt;/DIM-1/region</li>
 *     <li>end &rarr; &lt;name&gt;/DIM1/region</li>
 * </ul>
 * and level.dat / data / playerdata live at the folder root.
 */
public class LevelSaveHandler extends AnvilSaveHandler {

    public LevelSaveHandler(File levelFolder) {
        super(levelFolder.getParentFile(), levelFolder.getName(), true, DataFixesManager.createFixer());
    }
}