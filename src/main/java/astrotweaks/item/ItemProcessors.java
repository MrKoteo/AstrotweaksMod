
package astrotweaks.item;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.event.ModelRegistryEvent;

import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.NonNullList;

import java.util.List;

import astrotweaks.creativetab.TabAstroTweaks;

import astrotweaks.ElementsAstrotweaksMod;

@ElementsAstrotweaksMod.ModElement.Tag
public class ItemProcessors extends ElementsAstrotweaksMod.ModElement {
	private static class ProcessorEntry {
		final String regName;
		final String modelBase;
		final int minMeta;
		final int maxMeta;
		final String tooltipPrefix;
		Item item;
		ProcessorEntry(String regName, String modelBase, int minMeta, int maxMeta, String tooltipPrefix) {
			this.regName = regName;
			this.modelBase = modelBase;
			this.minMeta = minMeta;
			this.maxMeta = maxMeta;
			this.tooltipPrefix = tooltipPrefix;
		}
	}

	private static final ProcessorEntry[] PROCESSOR_DATA = {
	    new ProcessorEntry("processor_tier", "processor_tier", 0, 9, "Tier: "),
	    new ProcessorEntry("quantum_processor", "quantum_processor", 1, 2, "Tier: ")
	};

	@GameRegistry.ObjectHolder("astrotweaks:processor_tier")
	public static final Item processor_tier = null;
	@GameRegistry.ObjectHolder("astrotweaks:quantum_processor")
	public static final Item quantum_processor = null;

	public ItemProcessors(ElementsAstrotweaksMod instance) {
		super(instance, 120);
	}

	@Override
	public void initElements() {
		for (ProcessorEntry entry : PROCESSOR_DATA) {
			Item item = createProcessorItem(entry);
			entry.item = item;
			elements.items.add(() -> item);
		}
	}

	private Item createProcessorItem(final ProcessorEntry entry) {
		final String regName = entry.regName;
		final int minMeta = entry.minMeta;
		final int maxMeta = entry.maxMeta;
		final String tooltipPrefix = entry.tooltipPrefix;
		return new Item() {
			{
				setHasSubtypes(true);
				setUnlocalizedName(regName);
				setRegistryName(regName);
				setCreativeTab(TabAstroTweaks.tab);
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

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	@Override
		public void registerModels(ModelRegistryEvent event) {
		for (ProcessorEntry entry : PROCESSOR_DATA) {
			Item item = entry.item;
			if (item == null) continue;
			for (int m = entry.minMeta; m <= entry.maxMeta; m++) {
				ModelLoader.setCustomModelResourceLocation(item, m, new ModelResourceLocation("astrotweaks:" + entry.modelBase + "_" + m, "inventory"));
			}
		}
	}
}
