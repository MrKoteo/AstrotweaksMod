
package astrotweaks;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.client.event.ModelRegistryEvent;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraft.util.ResourceLocation;
import java.util.UUID;

import net.minecraft.world.biome.Biome;
import net.minecraft.potion.Potion;
import net.minecraft.item.Item;
import net.minecraft.block.Block;



import astrotweaks.procedure.FoodEffectHandler;


import astrotweaks.world.CavernMobModifier;
import astrotweaks.world.GrassGrowth;
import astrotweaks.recipe.CombinedFuelHandler;
import astrotweaks.util.Handler;



import astrotweaks.world.BushDecorator;
import astrotweaks.gameplay.RealisticBreak;

import astrotweaks.recipe.RecipeHandler;

import astrotweaks.ModVariables;
import astrotweaks.creativetab.ATCreativeTabs;

import java.util.function.Supplier;


@Mod(modid = AstrotweaksMod.MODID, version = AstrotweaksMod.VERSION)
public class AstrotweaksMod {

	public static final String MODID = "astrotweaks";
	public static final String VERSION = "b6.0";


	public static final SimpleNetworkWrapper PACKET_HANDLER = NetworkRegistry.INSTANCE.newSimpleChannel("astrotweaks:a");
	@SidedProxy(clientSide = "astrotweaks.ClientProxyAstrotweaksMod", serverSide = "astrotweaks.ServerProxyAstrotweaksMod")
	public static IProxyAstrotweaksMod proxy;
	@Mod.Instance(MODID)
	public static AstrotweaksMod instance;
	public ElementsAstrotweaksMod elements = new ElementsAstrotweaksMod();
	// ####################################################################################################

	public AstrotweaksMod() {

		ConfigManager.loadConfig();
	}

	@Mod.EventHandler
	public void construction(FMLConstructionEvent event) {
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);

        astrotweaks.ModVariables.preInit(event);


		

		GameRegistry.registerWorldGenerator(elements, 5);
		//GameRegistry.registerFuelHandler(elements);

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new Handler.GuiHandler());
		elements.preInit(event);
		MinecraftForge.EVENT_BUS.register(elements);
		elements.getElements().forEach(element -> element.preInit(event));
		proxy.preInit(event);

		if (ModVariables.Enable_Depths_Dimension) {
			astrotweaks.world.DepthsDim.preInit();
		}




		if (ModVariables.Enable_SnowVillages) {
	        astrotweaks.world.SnowVillage.preInit();
		}
		if (ModVariables.Enable_ForestVillages) {
	        astrotweaks.world.ForestVillage.preInit();
		}



	}

	//private SnowVillage snowVillage;
	//private ForestVillage forestVillage;
	//private DepthsDim depthsDim;
	//private ConfigManager cfg;
	private RealisticBreak realBreak;


	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		elements.getElements().forEach(element -> element.init(event));
		proxy.init(event);
		astrotweaks.ModVariables.init();

		ATCreativeTabs.init();

		astrotweaks.oredict.UOredictRegistrar.init();
		astrotweaks.oredict.OreDictQuantsT.init();
		astrotweaks.recipe.RecipeSmeltingAll.init();


		if (ModVariables.Enable_Depths_Dimension) MinecraftForge.EVENT_BUS.register(new CavernMobModifier());

		if (ModVariables.Enable_Bushes) {
			BushDecorator.init();
			MinecraftForge.TERRAIN_GEN_BUS.register(new BushDecorator());
		}

	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);

		astrotweaks.ModVariables.postInit();


		if (ModVariables.Enable_RealisticBreak) {
			realBreak = new RealisticBreak();
	        realBreak.postInit(event);
		}
		

		GrassGrowth.reloadFromConfig();

	}



	@Mod.EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		elements.getElements().forEach(element -> element.serverLoad(event));
		proxy.serverLoad(event);
	}

	
	@SubscribeEvent
	public void onRegisterRecipes(RegistryEvent.Register<IRecipe> event) {
	    RecipeHandler.loadRecipes();
	    IForgeRegistry<IRecipe> reg = event.getRegistry();
	    int idx = 1;
	    for (IRecipe r : RecipeHandler.RECIPES_TO_REGISTER) {
	        if (r.getRegistryName() == null) {
	            r.setRegistryName(new ResourceLocation("astrotweaks", "cr_" + idx));
	        }
	        reg.register(r);
	        idx++;
	    }
	    RecipeHandler.RECIPES_TO_REGISTER.clear();
	}


	@Mod.EventHandler
	public void onLoadComplete(FMLLoadCompleteEvent event) {
		if (ModVariables.Remove_METS_engineer)
			astrotweaks.tweaks.RemVillagerTrades.onLoadComplete();
	}




	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(elements.getBlocks().stream().map(Supplier::get).toArray(Block[]::new));
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(elements.getItems().stream().map(Supplier::get).toArray(Item[]::new));
	}

	@SubscribeEvent
	public void registerBiomes(RegistryEvent.Register<Biome> event) {
		event.getRegistry().registerAll(elements.getBiomes().stream().map(Supplier::get).toArray(Biome[]::new));
	}

	@SubscribeEvent
	public void registerEntities(RegistryEvent.Register<EntityEntry> event) {
		event.getRegistry().registerAll(elements.getEntities().stream().map(Supplier::get).toArray(EntityEntry[]::new));
	}

	@SubscribeEvent
	public void registerPotions(RegistryEvent.Register<Potion> event) {
		event.getRegistry().registerAll(elements.getPotions().stream().map(Supplier::get).toArray(Potion[]::new));
	}

	//@SubscribeEvent
	//public void registerSounds(RegistryEvent.Register<net.minecraft.util.SoundEvent> event) {
	//	elements.registerSounds(event);
	//}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void registerModels(ModelRegistryEvent event) {
		elements.getElements().forEach(element -> element.registerModels(event));
	}
	//static {
	//	FluidRegistry.enableUniversalBucket();
	//}
}
