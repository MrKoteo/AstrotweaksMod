package astrotweaks.tweaks;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import astrotweaks.ModVariables;



@Mod.EventBusSubscriber(modid = "astrotweaks")
public final class EventDropT { /* Это твик для удаления бумажки о смерти Gravestone */
    // item reg name
    private static final String TARGET_ITEM_ID = "gravestone:death_info";

    private static Item targetItem = null;

    private static Item getTargetItem() {
        if (targetItem == null && !TARGET_ITEM_ID.isEmpty()) {
            targetItem = GameRegistry.findRegistry(Item.class).getValue(new ResourceLocation(TARGET_ITEM_ID));
        }
        return targetItem;
    }

    @SubscribeEvent
    public static void onItemToss(ItemTossEvent event) {
    	if (!ModVariables.Rem_Gravestone_Note) return;
        if (event.getEntity().world.isRemote) return;

        ItemStack stack = event.getEntityItem().getItem();
        if (stack.isEmpty()) return;

        Item target = getTargetItem();
        if (target != null && stack.getItem() == target) {
            // del item
            event.getEntityItem().setDead();
            // cancel event to prevent play animation
            event.setCanceled(true);
        }
    }
}