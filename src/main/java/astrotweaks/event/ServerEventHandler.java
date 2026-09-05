package astrotweaks.event;


import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.world.BlockEvent;
import astrotweaks.block.BlockQmBlock;



@Mod.EventBusSubscriber(modid = "astrotweaks",value=Side.SERVER)
public class ServerEventHandler {
	@SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getState().getBlock() == BlockQmBlock.block) {
            event.setCanceled(true);
        }
    }
}
