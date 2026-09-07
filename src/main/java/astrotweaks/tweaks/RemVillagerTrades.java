package astrotweaks.tweaks;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.registries.IForgeRegistry;

//import astrotweaks.ModVariables;

import java.lang.reflect.Field;
import java.util.List;


public final class RemVillagerTrades {

    public static void onLoadComplete() {
        //if (!ModVariables.Remove_METS_engineer) return;
        if (!Loader.isModLoaded("mets")) return;

        try {
            ResourceLocation professionName = new ResourceLocation("mets", "engineer");

            IForgeRegistry<VillagerProfession> registry = GameRegistry.findRegistry(VillagerProfession.class);
            if (registry == null) {
                FMLLog.warning("VillagerProfession registry not found");
                return;
            }
            VillagerProfession profession = registry.getValue(professionName);
            if (profession == null) {
                FMLLog.info("Profession %s not found", professionName);
                return;
            }
            // careers (List<VillagerCareer>)
            Field careersField = findFirstListField(VillagerProfession.class, profession, VillagerCareer.class);
            if (careersField == null) {
                FMLLog.info("No careers list found for %s", professionName);
                return;
            }
            @SuppressWarnings("unchecked")
            List<VillagerCareer> careers = (List<VillagerCareer>) careersField.get(profession);
            if (careers == null || careers.isEmpty()) {
                FMLLog.info("No careers for %s", professionName);
                return;
            }
            //  trades (List<List<ITradeList>>)
            Field tradesField = findFirstListField(VillagerCareer.class, null, null);
            if (tradesField == null) {
                FMLLog.info("No trades field found in VillagerCareer");
                return;
            }
            for (VillagerCareer career : careers) {
                Object tradesObj = tradesField.get(career);
                if (tradesObj instanceof List) {
                    ((List<?>) tradesObj).clear();
                    FMLLog.info("Cleared trades for career: %s", career.getName());
                }
            }
            FMLLog.info("Successfully removed all trades for %s", professionName);
        } catch (Exception e) {
            FMLLog.info("Failed: %s", e.toString());
            e.printStackTrace();
        }
    }
    private static Field findFirstListField(Class<?> clazz, Object instance, Class<?> expectedElementType) {
        for (Field f : clazz.getDeclaredFields()) {
            if (List.class.isAssignableFrom(f.getType())) {
                f.setAccessible(true);
                if (instance != null && expectedElementType != null) {
                    try {
                        List<?> list = (List<?>) f.get(instance);
                        if (list != null && !list.isEmpty() && expectedElementType.isAssignableFrom(list.get(0).getClass())) {
                            return f;
                        }
                    } catch (IllegalAccessException e) {
                    }
                } else {
                    return f;
                }
            }
        }
        return null;
    }
}
