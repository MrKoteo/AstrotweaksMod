package astrotweaks.event;

import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraft.block.Block;
import java.util.IdentityHashMap;
import java.util.Map;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;



import astrotweaks.block.BlockBush1;
//import astrotweaks.block.BlockBush2;
import astrotweaks.block.BlockBush6;
import astrotweaks.block.BlockGiantGrass;

import astrotweaks.ModVariables;



@Mod.EventBusSubscriber(modid="astrotweaks",value=Side.CLIENT)
public class ClientEventHandler {
    private static final boolean NO_RED_FLASH = ModVariables.NoRedFlash;

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
                return 0xFFFFFF;
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



    // NoRedFlash (чтобы при уроне модельки мобов не краснели)

    private static final Map<EntityLivingBase, Integer> SAVED_HURT_TIME = new IdentityHashMap<>();
    @SubscribeEvent
    public static void onRenderLivingPre(RenderLivingEvent.Pre<?> event) {
        if (!NO_RED_FLASH) return;
        EntityLivingBase entity = event.getEntity();
        if (entity.hurtTime > 0) {
            SAVED_HURT_TIME.put(entity, Integer.valueOf(entity.hurtTime));
            entity.hurtTime = 0;
        } 
    }
    @SubscribeEvent
    public static void onRenderLivingPost(RenderLivingEvent.Post<?> event) {
        if (!NO_RED_FLASH) return;
        Integer saved = SAVED_HURT_TIME.remove(event.getEntity());
        if (saved != null) (event.getEntity()).hurtTime = saved.intValue(); 
    }


}
