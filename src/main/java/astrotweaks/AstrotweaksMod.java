
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
//import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraft.util.ResourceLocation;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraft.potion.Potion;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import astrotweaks.tech.qts.SuppressorEventHandler;

import astrotweaks.world.CavernMobModifier;
import astrotweaks.world.DecorateGroungElements;
import astrotweaks.world.GrassGrowth;
import astrotweaks.gui.GUIHandler;
import astrotweaks.procedure.FoodEffectHandler;
import astrotweaks.procedure.MineDimEnter;
import astrotweaks.world.BlockWorldGen;
import astrotweaks.world.BushDecorator;
import astrotweaks.gameplay.LetMeDisconnect;
import astrotweaks.gameplay.RealisticBreak;
import astrotweaks.gameplay.StepUp;
import astrotweaks.recipe.CombinedFuelHandler;
import astrotweaks.recipe.RecipeHandler;
import astrotweaks.creativetab.ATCreativeTabs;




@Mod(modid = AstrotweaksMod.MODID, version = AstrotweaksMod.VERSION)
public class AstrotweaksMod {

	public static final String MODID = "astrotweaks";
	public static final String VERSION = "b6.0";


	public static final SimpleNetworkWrapper PACKET_HANDLER = NetworkRegistry.INSTANCE.newSimpleChannel("astrotweaks:a");
	static {
		PACKET_HANDLER.registerMessage(ModVariables.WorldSavedDataSyncMessageHandler.class, ModVariables.WorldSavedDataSyncMessage.class, 0, Side.SERVER);
		PACKET_HANDLER.registerMessage(ModVariables.WorldSavedDataSyncMessageHandler.class, ModVariables.WorldSavedDataSyncMessage.class, 0, Side.CLIENT);
	}
	@SidedProxy(clientSide = "astrotweaks.ClientProxyAstrotweaksMod", serverSide = "astrotweaks.ServerProxyAstrotweaksMod")
	public static IProxyAstrotweaksMod proxy;
	@Mod.Instance(MODID)
	public static AstrotweaksMod instance;
	//public ElementsAstrotweaksMod elements = new ElementsAstrotweaksMod();
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

		astrotweaks.ModVariables.preInit();


		

		//GameRegistry.registerWorldGenerator(elements, 5);
		//GameRegistry.registerFuelHandler(elements);

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GUIHandler.GuiHandler());
		//MinecraftForge.EVENT_BUS.register(elements);
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

		if (ModVariables.Enable_Ground_Elements) astrotweaks.world.DecorateGroungElements.register();
		astrotweaks.world.BlockWorldGen.register();


		// BUS  events
		MinecraftForge.EVENT_BUS.register(new astrotweaks.event.EventLoadWorld());


		if (ModVariables.Extra_Fuels) MinecraftForge.EVENT_BUS.register(new CombinedFuelHandler());
		if (ModVariables.Enable_Depths_Dimension) MinecraftForge.EVENT_BUS.register(new CavernMobModifier());
		if (ModVariables.Enable_StepUp) MinecraftForge.EVENT_BUS.register(new StepUp());
		if (ModVariables.Food_Negative_Effects) MinecraftForge.EVENT_BUS.register(new FoodEffectHandler());
		if (ModVariables.GG_ENABLED) MinecraftForge.EVENT_BUS.register(new GrassGrowth());
    	if (ModVariables.Enable_Depths_Dim_Bedrock_TP) MinecraftForge.EVENT_BUS.register(new MineDimEnter());


	}

	//private DepthsDim depthsDim;
	//private ConfigManager cfg;
	//private RealisticBreak realBreak;


	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
		astrotweaks.ModVariables.init();

		ATCreativeTabs.init();

		astrotweaks.oredict.UOredictRegistrar.init();
		astrotweaks.oredict.OreDictQuantsT.init();
		astrotweaks.recipe.RecipeSmeltingAll.init();



		if (ModVariables.Enable_Bushes) {
			BushDecorator.init();
			MinecraftForge.TERRAIN_GEN_BUS.register(new BushDecorator());
		}

		astrotweaks.recipe.GavelRecipeRegistry.initDefaults();


		MinecraftForge.EVENT_BUS.register(new SuppressorEventHandler()); // init

		if (ModVariables.Enable_RealisticBreak) {
			astrotweaks.gameplay.RealisticBreak.postInit();
			MinecraftForge.EVENT_BUS.register(new RealisticBreak());
		}


	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);

		astrotweaks.ModVariables.postInit();





		//GrassGrowth.reloadFromConfig();


		MinecraftForge.EVENT_BUS.register(new LetMeDisconnect());

	}



	@Mod.EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		astrotweaks.command.ATCommands.init(event);
		//elements.getElements().forEach(element -> element.serverLoad(event));
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
		RecipeHandler.RECIPES_TO_REGISTER.clear(); // удаляем мусор из памяти
	}


	@Mod.EventHandler
	public void onLoadComplete(FMLLoadCompleteEvent event) {
		if (ModVariables.Remove_METS_engineer)
			astrotweaks.tweaks.RemVillagerTrades.onLoadComplete();


		ClearRegArrays();
	}







	@SubscribeEvent
	public void onPlayerLoggedIn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event) {
		if (!event.player.world.isRemote) {
			WorldSavedData mapdata = ModVariables.MapVariables.get(event.player.world);
			WorldSavedData worlddata = ModVariables.WorldVariables.get(event.player.world);
			if (mapdata != null)
				AstrotweaksMod.PACKET_HANDLER.sendTo(new ModVariables.WorldSavedDataSyncMessage(0, mapdata), (EntityPlayerMP) event.player);
			if (worlddata != null)
				AstrotweaksMod.PACKET_HANDLER.sendTo(new ModVariables.WorldSavedDataSyncMessage(1, worlddata), (EntityPlayerMP) event.player);
		}
	}

	@SubscribeEvent
	public void registerBiomes(RegistryEvent.Register<Biome> event) {
		//event.getRegistry().registerAll(elements.getBiomes().stream().map(Supplier::get).toArray(Biome[]::new));
		if (ModVariables.Enable_Depths_Dimension) event.getRegistry().register(astrotweaks.world.biome.BiomeCavern.CAVERN);


	}

	//@SubscribeEvent
	//public void registerEntities(RegistryEvent.Register<EntityEntry> event) {
		//event.getRegistry().registerAll(elements.getEntities().stream().map(Supplier::get).toArray(EntityEntry[]::new));
	//}

	//@SubscribeEvent
	//public void registerPotions(RegistryEvent.Register<Potion> event) {
		//event.getRegistry().registerAll(elements.getPotions().stream().map(Supplier::get).toArray(Potion[]::new));
	//}

	//@SubscribeEvent
	//public void registerSounds(RegistryEvent.Register<net.minecraft.util.SoundEvent> event) {
	//	elements.registerSounds(event);
	//}

	//@SubscribeEvent
	//@SideOnly(Side.CLIENT)
	//public void registerModels(ModelRegistryEvent event) {
		//elements.getElements().forEach(element -> element.registerModels(event));
	//}
	//static {
	//	FluidRegistry.enableUniversalBucket();
	//}


	// GC не очищает неипользуемые переменные классов, поэтому чистим их вручную т.к. они больше не нужны после регистрации
	public static void ClearRegArrays() {
		// Список рецептов к регистрации
		

		astrotweaks.block.ATBlocks.ClearRegList();
		astrotweaks.item.ATItems.ClearRegList();


	}


}
