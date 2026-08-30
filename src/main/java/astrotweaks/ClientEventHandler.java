package astrotweaks.event;

import astrotweaks.block.BlockBush1;
import astrotweaks.block.BlockBush2;
import astrotweaks.block.BlockBush6;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraft.block.Block;



@Mod.EventBusSubscriber(modid="astrotweaks",value=Side.CLIENT)
public class ClientEventHandler {
    static final Block[] GRASS_COLOR_BLOCKS = new Block[] {
        BlockBush1.block,
        //BlockBush2.block,
        BlockBush6.block

    };

    @SubscribeEvent
    public static void onBlockColors(ColorHandlerEvent.Block event) {
        event.getBlockColors().registerBlockColorHandler(
            (state, worldIn, pos, tintIndex) -> {
                if (worldIn != null && pos != null) {
                    return BiomeColorHelper.getGrassColorAtPos(worldIn, pos);
                }
                return 0x337722;
            },
            GRASS_COLOR_BLOCKS // varargs array
        );
    }
}