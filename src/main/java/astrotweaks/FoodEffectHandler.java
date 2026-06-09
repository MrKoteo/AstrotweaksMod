package astrotweaks.procedure;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;


import astrotweaks.AstrotweaksModVariables;
import astrotweaks.ModVariables;


@Mod.EventBusSubscriber
public class FoodEffectHandler {
    // map  "item -> effect"
    private static Map<ItemStack, PotionData[]> FOOD_EFFECTS = new HashMap<>();

	public static void init() {
		if (!(AstrotweaksModVariables.Food_Negative_Effects)) { FOOD_EFFECTS = Collections.emptyMap();return; }

		Map<ItemStack, PotionData[]> map = new HashMap<>();
		
        // register
        // Format:  Name, Time in Ticks, Level
        addEffects(map, new ItemStack(net.minecraft.init.Items.ROTTEN_FLESH),
            potion(MobEffects.WEAKNESS, 600, 0),
            potion(MobEffects.MINING_FATIGUE, 800, 0),
            potion(MobEffects.HUNGER, 1800, 1),
            potion(MobEffects.SLOWNESS, 600, 0),
            potion(MobEffects.NAUSEA, 200, 0),
            potion(MobEffects.POISON, 200, 0)
        );
        addEffects(map, new ItemStack(net.minecraft.init.Items.POISONOUS_POTATO),
            potion(MobEffects.POISON, 200, 2),
            potion(MobEffects.WEAKNESS, 600, 0),
            potion(MobEffects.MINING_FATIGUE, 1200, 0),
            potion(MobEffects.HUNGER, 800, 3),
            potion(MobEffects.SLOWNESS, 600, 0),
            potion(MobEffects.NAUSEA, 400, 1)
        );
        addEffects(map, new ItemStack(net.minecraft.init.Items.SPIDER_EYE),
            potion(MobEffects.POISON, 300, 1),
            potion(MobEffects.WEAKNESS, 1200, 0),
            potion(MobEffects.MINING_FATIGUE, 1200, 0),
            potion(MobEffects.HUNGER, 600, 2),
            potion(MobEffects.SLOWNESS, 600, 0),
            potion(MobEffects.NAUSEA, 100, 0)
        );
        // RAW meat
        if (ModVariables.Raw_Meat_Negative_Effects) {
            PotionData[] meatEffects = {
                potion(MobEffects.WEAKNESS, 400, 0),
                potion(MobEffects.MINING_FATIGUE, 800, 0),
                potion(MobEffects.HUNGER, 1800, 1),
                potion(MobEffects.POISON, 160, 0),
                potion(MobEffects.SLOWNESS, 30, 1)
            };
            for (ItemStack meatItem : ModVariables.MEAT_LIST) {
                if (meatItem != null) {
                    addEffects(map, meatItem, meatEffects);
                }
            }
        }

        FOOD_EFFECTS = Collections.unmodifiableMap(map);
    }

    private static final PotionData potion(net.minecraft.potion.Potion potion, int duration, int amplifier) {
        return new PotionData(potion, duration, amplifier);
    }
    private static void addEffects(Map<ItemStack, PotionData[]> map, ItemStack item, PotionData... effects) {
        map.put(item, effects);
    }

    @SubscribeEvent
    public static void onFoodEaten(LivingEntityUseItemEvent.Finish event) {
    	if (Math.random() < 0.25) return;
        if (!(event.getEntityLiving() instanceof EntityPlayer)) return;
        ItemStack eaten = event.getItem();
        if (eaten.isEmpty()) return;

        for (Map.Entry<ItemStack, PotionData[]> entry : FOOD_EFFECTS.entrySet()) {
            if (entry.getKey().isItemEqual(eaten)) {
                EntityPlayer player = (EntityPlayer) event.getEntityLiving();
                for (PotionData data : entry.getValue()) {
                    player.addPotionEffect(new PotionEffect(data.potion, data.duration, data.amplifier));
                }
                break;
            }
        }
    }
    private static class PotionData {
        public final net.minecraft.potion.Potion potion;
        public final int duration;
        public final int amplifier;
        public PotionData(net.minecraft.potion.Potion potion, int duration, int amplifier) {
            this.potion = potion;
            this.duration = duration;
            this.amplifier = amplifier;
        }
    }
}