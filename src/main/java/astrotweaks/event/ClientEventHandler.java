package astrotweaks.event;

import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraft.block.Block;

import astrotweaks.block.BlockBush1;
//import astrotweaks.block.BlockBush2;
import astrotweaks.block.BlockBush6;
import astrotweaks.block.BlockGiantGrass;




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
            (state, world, pos, tintIndex) -> {
                if (world != null && pos != null) {
                    return BiomeColorHelper.getGrassColorAtPos(world, pos);
                }
                return 0x367A25;
            },
            GRASS_COLOR_BLOCKS // varargs array
        );
        event.getBlockColors().registerBlockColorHandler(
            (state, world, pos, tintIndex) -> {
                if (world != null && pos != null) {
                    return BiomeColorHelper.getGrassColorAtPos(world, pos);
                }
                //    e.printStackTrace();
                //    System.out.println("сука краш");
                //}
                return 0x94C774;
            },
            BlockGiantGrass.block
        );
    }

    @SubscribeEvent
    public static void onItemColors(ColorHandlerEvent.Item event) {
        event.getItemColors().registerItemColorHandler(
            (stack, tintIndex) -> {
                // Для инвентаря используем цвет по умолчанию (например, зелёный)
                return 0x94C774;
            },
            BlockGiantGrass.block
        );
    }
}
