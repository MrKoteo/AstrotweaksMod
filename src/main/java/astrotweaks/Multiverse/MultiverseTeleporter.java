package astrotweaks.Multiverse;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

/** Places the entity at a fixed position, skipping the nether-portal math of Teleporter. */
public class MultiverseTeleporter implements ITeleporter {

    private final BlockPos pos;

    public MultiverseTeleporter(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public void placeEntity(World world, Entity entity, float yaw) {
        entity.setPositionAndUpdate(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5);
    }

    @Override
    public boolean isVanilla() {
        return false;
    }
}