package astrotweaks.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import astrotweaks.creativetab.ATCreativeTabs;

@Mod.EventBusSubscriber(modid = "astrotweaks")
public final class ItemProcessors {

    public static final int PROCESSOR_TIER_MIN_META = 0;
    public static final int PROCESSOR_TIER_MAX_META = 9;
    public static final int QUANTUM_PROCESSOR_MIN_META = 1;
    public static final int QUANTUM_PROCESSOR_MAX_META = 2;

    public static final Item PROCESSOR_TIER = createProcessorItem("processor_tier", PROCESSOR_TIER_MIN_META, PROCESSOR_TIER_MAX_META, "Tier: ");
    public static final Item QUANTUM_PROCESSOR = createProcessorItem("quantum_processor", QUANTUM_PROCESSOR_MIN_META, QUANTUM_PROCESSOR_MAX_META, "Tier: ");

    private ItemProcessors() {
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(PROCESSOR_TIER, QUANTUM_PROCESSOR);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (int m = PROCESSOR_TIER_MIN_META; m <= PROCESSOR_TIER_MAX_META; m++) {
            ModelLoader.setCustomModelResourceLocation(PROCESSOR_TIER, m, new net.minecraft.client.renderer.block.model.ModelResourceLocation("astrotweaks:processor_tier_" + m, "inventory"));
        }
        for (int m = QUANTUM_PROCESSOR_MIN_META; m <= QUANTUM_PROCESSOR_MAX_META; m++) {
            ModelLoader.setCustomModelResourceLocation(QUANTUM_PROCESSOR, m, new net.minecraft.client.renderer.block.model.ModelResourceLocation("astrotweaks:quantum_processor_" + m, "inventory"));
        }
    }

    private static Item createProcessorItem(final String regName, final int minMeta, final int maxMeta, final String tooltipPrefix) {
        return new Item() {
            {
                setHasSubtypes(true);
                setUnlocalizedName(regName);
                setRegistryName("astrotweaks", regName);
                setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
            }

            @Override
            public int getMetadata(int damage) {
                return damage;
            }

            @Override
            public String getUnlocalizedName(ItemStack stack) {
                int meta = stack.getMetadata();
                if (meta >= minMeta && meta <= maxMeta) return "item." + regName + "_" + meta;
                return "item." + regName + ".name";
            }

            @Override
            public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
                if (this.isInCreativeTab(tab)) {
                    for (int m = minMeta; m <= maxMeta; m++) {
                        items.add(new ItemStack(this, 1, m));
                    }
                }
            }

            @Override
            public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag) {
                super.addInformation(itemstack, world, list, flag);
                int meta = itemstack.getMetadata();
                if (meta >= minMeta && meta <= maxMeta) list.add(tooltipPrefix + meta);
            }
        };
    }
}