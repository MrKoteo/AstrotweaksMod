package astrotweaks;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import java.io.File;

import astrotweaks.AstrotweaksMod;


public class ConfigManager {
	private static final org.apache.logging.log4j.Logger LOGGER = FMLLog.getLogger();
	private static Configuration config;
	public ConfigManager() {}


    // Вызвать один раз на этапе construction
    public static void loadConfig() {
        File configFile = new File("config/" + AstrotweaksMod.MODID + ".cfg");
        config = new Configuration(configFile);
        try {
            config.load();
            loadValues();  // читаем все переменные
        } catch (Exception e) {
            LOGGER.error("Failed to load config", e);
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
    }

	public static void loadValues() {

		//Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		try {
			config.load();

			//# Категории

			// Astro_Tech 		- то что неотъемлемо связано с модпаком
			// general 			- глобальные функции
			// Game mechanics 	- игровые механики
			// Worldgen 		- генерация мира
			// World			- В целом то, что расширяет мир
			// mods 			- изменение других модов
			// tweaks			- различные твики и QoLF
			// Natures Power	- То что меняет мир с течением времени, like рост травы, мха, итд.
			//
			// misc 			- Всё что не вошло в обычные категории
			//
			//
			//



			//// # Astro_Tech
			ModVariables.AstroTech_Environment = safeGetBoolean(config, "AstroTech_Environment", "Astro_Tech", false, "AstroTech Environment (y/n)");
			ModVariables.EnableProgressionSystem = safeGetBoolean(config, "EnableProgressionSystem", "Astro_Tech", false, "Enable Progression System (y/n)");



			//// # general
			ModVariables.doRegisterMinedBlocks = safeGetBoolean(config, "doRegisterMinedBlocks", "general", ModVariables.doRegisterMinedBlocks, "Register mined(trapped) blocks? (y/n)");
			ModVariables.Extra_Fuels = safeGetBoolean(config, "Register_Extra_Fuels", "misc", ModVariables.Extra_Fuels, "Should to register more fuels for furnace? (y/n)");



			//// # Game mechanics
			ModVariables.Enable_RealisticBreak = safeGetBoolean(config, "Enable_RealisticBreak", "Game mechanics", ModVariables.Enable_RealisticBreak, "More realistic conditions for destruction of blocks");
			ModVariables.Enable_StepUp = safeGetBoolean(config, "StepUp", "Game mechanics", ModVariables.Enable_StepUp, "1-block high step, without auto-jump");
			ModVariables.Enable_Dirt2Path = safeGetBoolean(config, "Dirt2Path", "Game mechanics", ModVariables.Enable_Dirt2Path, "Allow convert dirt/podzol/mycelium to GrassPath");
			ModVariables.Food_Negative_Effects = safeGetBoolean(config, "Food_Negative_Effects", "Game mechanics", ModVariables.Food_Negative_Effects, "Will poisonous food have more violent effects? (y/n)");
			ModVariables.Raw_Meat_Negative_Effects = safeGetBoolean(config, "Raw_Meat_Negative_Effects", "Game mechanics", ModVariables.Raw_Meat_Negative_Effects, "Will raw meat be less edible? (y/n)");



			//// # Worldgen
			ModVariables.Enable_SnowVillages = safeGetBoolean(config, "Enable_SnowVillages", "Worldgen", ModVariables.Enable_SnowVillages, "Enable Snow Villages generation (y/n)");
			ModVariables.Enable_ForestVillages = safeGetBoolean(config, "Enable_ForestVillages", "Worldgen", ModVariables.Enable_ForestVillages, "Enable Forest Villages generation (y/n)");
			//ModVariables.Enable_BirchVillages = safeGetBoolean(config, "Enable_BirchVillages", "Worldgen", true, "Enable Birch Forest Villages generation (y/n)");
			ModVariables.OW_Quartz_Gen = safeGetBoolean(config, "Overworld_Quartz_Generation", "Worldgen", ModVariables.OW_Quartz_Gen, "Enable Overworld Quartz Generation (y/n)");
			ModVariables.OW_Ruby_Gen = safeGetBoolean(config, "Ruby_Generation", "Worldgen", ModVariables.OW_Ruby_Gen, "Enable Ruby Generation (y/n)");
			ModVariables.OW_Minerals_Gen = safeGetBoolean(config, "Overworld_Minerals_Generation", "Worldgen", ModVariables.OW_Minerals_Gen, "Enable Overworld Minerals Generation (y/n)");
			ModVariables.Enable_Bushes = safeGetBoolean(config, "Enable_Bushes", "Worldgen", ModVariables.Enable_Bushes, "Enable bush generation in Overworld (y/n)");
			ModVariables.Enable_Ground_Elements = safeGetBoolean(config, "Enable_Ground_Elements", "Worldgen", ModVariables.Enable_Ground_Elements, "Enable Ground elements generation (y/n)");
			// Double values with validation of range and parsing
			ModVariables.Stick_Gen_Attempts = safeGetDouble(config, "Stick_Gen_Attempts", "Worldgen", ModVariables.Stick_Gen_Attempts /*def*/, 0.0 /*min*/, 999.0 /*max*/, "Number of attempts to generate an element (double num) [default: 2.0]");
			String[] StickBiomes = config.get("Worldgen", "Stick_Gen_Biomes", STICK_BIOME_LIST, 
				"List of biome IDs (e.g., minecraft:plains) where sticks generate. " + "Empty list uses default biomes.").getStringList();
			ModVariables.Stick_Gen_Min_Y = safeGetInt(config, "Stick_Gen_Min_Y", "Worldgen", ModVariables.Stick_Gen_Min_Y, 1, 255, "Minimum Y level for stick generation (1-255)");
			ModVariables.Stick_Gen_Max_Y = safeGetInt(config, "Stick_Gen_Max_Y", "Worldgen", ModVariables.Stick_Gen_Max_Y, 1, 255, "Maximum Y level for stick generation (1-255)");
			ModVariables.Rock_Gen_Attempts = safeGetDouble(config, "Rock_Gen_Attempts", "Worldgen", ModVariables.Rock_Gen_Attempts /*def*/, 0.0 /*min*/, 999.0 /*max*/, "Number of attempts to generate an element (double num) [default: 0.3]");
			String[] RockBiomes = config.get("Worldgen", "Rock_Gen_Biomes", ROCK_BIOME_LIST, 
				"List of biome IDs (e.g., minecraft:plains) where rocks generate. " + "Empty list uses default biomes.").getStringList();
			ModVariables.Rock_Gen_Min_Y = safeGetInt(config, "Rock_Gen_Min_Y", "Worldgen", ModVariables.Rock_Gen_Min_Y, 1, 255, "Minimum Y level for rock generation (1-255)");
			ModVariables.Rock_Gen_Max_Y = safeGetInt(config, "Rock_Gen_Max_Y", "Worldgen", ModVariables.Rock_Gen_Max_Y, 1, 255, "Maximum Y level for rock generation (1-255)");
			

			//// # World
			ModVariables.Enable_Depths_Dimension = safeGetBoolean(config, "Enable_Depths_Dimension", "World", ModVariables.Enable_Depths_Dimension, "Should register Depths dimension? (y/n)");
			ModVariables.Enable_Depths_Dim_Bedrock_TP = safeGetBoolean(config, "Enable_Depths_Dim_Bedrock_TP", "World", ModVariables.Enable_Depths_Dim_Bedrock_TP, "Allow access to the Depths via Bedrock? (y/n)");

			
			//// # Tweaks
			ModVariables.NoRedFlash = safeGetBoolean(config, "No_Red_Flash", "Tweaks", ModVariables.NoRedFlash, "Remove entities red flash when taking damage (y/n)");
			ModVariables.No_Damage_Shaking = safeGetBoolean(config, "No_Damage_Shaking", "Tweaks", ModVariables.No_Damage_Shaking, "Removes the player's screen shake when taking damage (y/n)");
			ModVariables.No_Potion_Icons = safeGetBoolean(config, "No_Potion_Icons", "Tweaks", ModVariables.No_Potion_Icons, "Disable Potion Icons in the top right of screen (y/n)");
			ModVariables.ServerPingFix = safeGetBoolean(config, "ServerPingFix", "Tweaks", ModVariables.ServerPingFix, "Like in mod \"FIX MY PINGGGGGG\" (y/n)");
			ModVariables.Better_Smelting = safeGetBoolean(config, "Better_Smelting", "Tweaks", ModVariables.Better_Smelting, "Enable more smelting recipes? (y/n)");
			ModVariables.ExplosionDamageMult = safeGetDouble(config, "Explosion_Damage_Multiplier", "Tweaks", ModVariables.ExplosionDamageMult, 0.0, 100.0, "Explosive damage modifier. (DAMAGE * Multiplier)");



			//// # Nature's Power
			ModVariables.GG_ENABLED = safeGetBoolean(config, "GG_ENABLED", "Natures Power", ModVariables.GG_ENABLED, "Enable Grass growth? (y/n)");
			ModVariables.GG_MIN_DELAY_TICK = safeGetInt(config, "Grass_Growth_MIN_Delay", "Natures Power", ModVariables.GG_MIN_DELAY_TICK, 1, 1728000, "Minimum delay (ticks) for grass regrowth (10-1728000)");
			ModVariables.GG_MAX_DELAY_TICK = safeGetInt(config, "Grass_Growth_MAX_Delay", "Natures Power", ModVariables.GG_MAX_DELAY_TICK, 2, 1728000, "Maximum delay (ticks) for grass regrowth (10-1728000)");
			ModVariables.GG_MAX_OPER_PER_TICK = safeGetInt(config, "GG_MAX_PER_TICK", "Natures Power", ModVariables.GG_MAX_OPER_PER_TICK, 1, 999, "Maximum operations per tick (1-999)");
			ModVariables.GG_Density = safeGetInt(config, "Grass_Density", "Natures Power", ModVariables.GG_Density, 1, 25, "Maximum grass density (1-25)");
			ModVariables.GG_Tall_Density = safeGetInt(config, "Tall_Grass_Density", "Natures Power", ModVariables.GG_Tall_Density, 1, 25, "Maximum tall grass (double_plant:2) density (1-25)");
			ModVariables.GG_Giant_Density = safeGetInt(config, "Giant_Graass_Density", "Natures Power", ModVariables.GG_Giant_Density, 1, 25, "Maximum giant grass density (1-25)");


			//// # Technologies
			ModVariables.QM_is_fully_unbreakable = safeGetBoolean(config, "QM_is_fully_unbreakable", "Game mechanics", ModVariables.QM_is_fully_unbreakable, "Prohibit the player from breaking the QM_block");
			ModVariables.QTS_Max_Range = safeGetInt(config, "QTS_Max_Range", "misc", ModVariables.QTS_Max_Range, 1, 16384, "Maximum range of Quantum TP Supressor (1-16384)");
			ModVariables.Money_Can_Smelt = safeGetBoolean(config, "Money_Can_Smelt", "misc", ModVariables.Money_Can_Smelt, "Can coins be melted down (y/n)");
			ModVariables.Money_Can_Craft = safeGetBoolean(config, "Money_Can_Craft", "misc", ModVariables.Money_Can_Craft, "Can copper coins be crafted at the MoneyTable from a copper plate (y/n)");
			ModVariables.Money_Can_Conversion = safeGetBoolean(config, "Money_Can_Conversion", "misc", ModVariables.Money_Can_Conversion, "Can coins be converted in Money Table? (y/n)");
			ModVariables.Money_ConvCount = safeGetInt(config, "Money_ConvCount", "misc", ModVariables.Money_ConvCount, 1, 50, "Maximum number of coins that can be processed at one conv (1-50)");



			//// # Mods
			ModVariables.Remove_METS_engineer = safeGetBoolean(config, "Remove_METS_engineer", "mods", ModVariables.Remove_METS_engineer, "Remove trades of Engineer villager (MoreElectricTools)");
			ModVariables.Rem_Gravestone_Note = safeGetBoolean(config, "Remove_Gravestone_Note", "mods", ModVariables.Rem_Gravestone_Note, "Removes a Gravestone paper when dropped by a player");





			//// # misc
			ModVariables.Extra_Drops_Grass = safeGetBoolean(config, "Extra_Drops_Grass", "misc", ModVariables.Extra_Drops_Grass, "Should process additional drops from grass? (y/n)");
			ModVariables.Extra_Drops_All = safeGetBoolean(config, "Extra_Drops_All", "misc", ModVariables.Extra_Drops_All, "Should extra drops be processed in general? (y/n)");








































			Set<ResourceLocation> sgb = new HashSet<>();
			Set<Biome> sgbObj = new HashSet<>();
			
			for (String s : StickBiomes) {
			    ResourceLocation rl = new ResourceLocation(s);
			    sgb.add(rl);
			    Biome b = Biome.REGISTRY.getObject(rl);
			    if (b != null) {
			        sgbObj.add(b);
			    }
			}
			
			ModVariables.Stick_Gen_Biomes = Collections.unmodifiableSet(sgb);
			ModVariables.Stick_Gen_Biomes_Cached = Collections.unmodifiableSet(sgbObj);
			///
			Set<ResourceLocation> rgb = new HashSet<>();
			Set<Biome> rgbObj = new HashSet<>();
			
			for (String s : RockBiomes) {
			    ResourceLocation rl = new ResourceLocation(s);
			    rgb.add(rl);
			    Biome b = Biome.REGISTRY.getObject(rl);
			    if (b != null) {
			        rgbObj.add(b);
			    }
			}
			ModVariables.Rock_Gen_Biomes = Collections.unmodifiableSet(rgb);
			ModVariables.Rock_Gen_Biomes_Cached = Collections.unmodifiableSet(rgbObj);



		} finally {
			if (config.hasChanged()) {
				config.save();
			}
		}
	}
	
	private static final String[] STICK_BIOME_LIST = new String[]{
	    "minecraft:forest", "minecraft:taiga", "minecraft:swampland",
	    "minecraft:forest_hills", "minecraft:taiga_hills", "minecraft:smaller_extreme_hills", "minecraft:jungle", "minecraft:jungle_hills", 
	    "minecraft:jungle_edge", "minecraft:birch_forest", "minecraft:birch_forest_hills", "minecraft:roofed_forest",
	    "minecraft:redwood_taiga", "minecraft:redwood_taiga_hills", "minecraft:extreme_hills_with_trees", "minecraft:savanna", "minecraft:savanna_rock",
		"minecraft:mutated_forest","minecraft:mutated_taiga","minecraft:mutated_swampland","minecraft:mutated_jungle",
		"minecraft:mutated_jungle_edge","minecraft:mutated_birch_forest","minecraft:mutated_birch_forest_hills","minecraft:mutated_roofed_forest","minecraft:mutated_redwood_taiga",
		"minecraft:mutated_redwood_taiga_hills","minecraft:mutated_extreme_hills_with_trees","minecraft:mutated_savanna","minecraft:mutated_savanna_rock"
	};
	private static final String[] ROCK_BIOME_LIST = new String[]{
	    "minecraft:plains", "minecraft:extreme_hills", "minecraft:forest", "minecraft:taiga", "minecraft:river", "minecraft:beaches", 
	    "minecraft:forest_hills", "minecraft:taiga_hills", "minecraft:smaller_extreme_hills", "minecraft:jungle", "minecraft:jungle_hills", 
	    "minecraft:jungle_edge", "minecraft:stone_beach", "minecraft:birch_forest", "minecraft:birch_forest_hills", "minecraft:roofed_forest",
	    "minecraft:redwood_taiga", "minecraft:redwood_taiga_hills", "minecraft:extreme_hills_with_trees", "minecraft:savanna", "minecraft:savanna_rock",
		"minecraft:mutated_plains","minecraft:mutated_extreme_hills","minecraft:mutated_forest","minecraft:mutated_taiga","minecraft:mutated_swampland","minecraft:mutated_jungle",
		"minecraft:mutated_jungle_edge","minecraft:mutated_birch_forest","minecraft:mutated_birch_forest_hills","minecraft:mutated_roofed_forest","minecraft:mutated_redwood_taiga",
		"minecraft:mutated_redwood_taiga_hills","minecraft:mutated_extreme_hills_with_trees","minecraft:mutated_savanna","minecraft:mutated_savanna_rock"
	};

/*
		"minecraft:mutated_plains","minecraft:mutated_extreme_hills","minecraft:mutated_forest","minecraft:mutated_taiga","minecraft:mutated_swampland","minecraft:mutated_jungle",
		"minecraft:mutated_jungle_edge","minecraft:mutated_birch_forest","minecraft:mutated_birch_forest_hills","minecraft:mutated_roofed_forest","minecraft:mutated_redwood_taiga",
		"minecraft:mutated_redwood_taiga_hills","minecraft:mutated_extreme_hills_with_trees","minecraft:mutated_savanna","minecraft:mutated_savanna_rock"
*/


	// --- helper methods ---

	private static boolean safeGetBoolean(Configuration config, String name, String category, boolean def, String comment) {
		try {
			// read as string first to detect malformed booleans like "yes" or "tru"
			String raw = config.get(category, name, Boolean.toString(def), comment).getString();
			// allow "true"/"false" (case-insensitive), also support "1"/"0" as convenience
			if (raw.equalsIgnoreCase("true") || raw.equals("1")) {
				return true;
			} else if (raw.equalsIgnoreCase("false") || raw.equals("0")) {
				return false;
			} else {
				LOGGER.warn("Config '{}' in category '{}' has invalid boolean value '{}'. Using default: {}", name, category, raw, def);
				// overwrite invalid value with default so it is saved back
				config.get(category, name, def).set(def);
				return def;
			}
		} catch (Exception e) {
			LOGGER.error("Error reading boolean config '{}.{}': {}", category, name, e.getMessage());
			config.get(category, name, def).set(def);
			return def;
		}
	}

	private static double safeGetDouble(Configuration config, String name, String category, double def, double min, double max, String comment) {
		try {
			String raw = config.get(category, name, Double.toString(def), comment).getString();
			double val;
			try {
				val = Double.parseDouble(raw);
			} catch (NumberFormatException nfe) {
				LOGGER.warn("Config '{}' in category '{}' has invalid double value '{}'. Using default: {}", name, category, raw, def);
				config.get(category, name, def).set(def);
				return def;
			}
			if (val < min || val > max) {
				LOGGER.warn("Config '{}' in category '{}' out of range ({}-{}): {}. Using default: {}", name, category, min, max, val, def);
				config.get(category, name, def).set(def);
				return def;
			}
			return val;
		} catch (Exception e) {
			LOGGER.error("Error reading double config '{}.{}': {}", category, name, e.getMessage());
			config.get(category, name, def).set(def);
			return def;
		}
	}

	private static int safeGetInt(Configuration config, String name, String category, int def, int min, int max, String comment) {
	    try {
	        String raw = config.get(category, name, Integer.toString(def), comment).getString();
	        int val;
	        try {
	            val = Integer.parseInt(raw);
	        } catch (NumberFormatException nfe) {
	            LOGGER.warn("Config '{}' in category '{}' has invalid integer value '{}'. Using default: {}", name, category, raw, def);
	            config.get(category, name, def).set(def);
	            return def;
	        }
	        if (val < min || val > max) {
	            LOGGER.warn("Config '{}' in category '{}' out of range ({}-{}): {}. Using default: {}", name, category, min, max, val, def);
	            config.get(category, name, def).set(def);
	            return def;
	        }
	        return val;
	    } catch (Exception e) {
	        LOGGER.error("Error reading integer config '{}.{}': {}", category, name, e.getMessage());
	        config.get(category, name, def).set(def);
	        return def;
	    }
	}
}
