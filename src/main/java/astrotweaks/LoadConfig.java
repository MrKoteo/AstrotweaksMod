package astrotweaks.util;

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

import astrotweaks.AstrotweaksModVariables;
import astrotweaks.ModVariables;


public class LoadConfig {
	private static final org.apache.logging.log4j.Logger LOGGER = FMLLog.getLogger();
	public LoadConfig() {}

	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);

		//System.out.println("abobba 1");

		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		try {
			config.load();

			// Boolean with safe fallback
			AstrotweaksModVariables.AstroTech_Environment = safeGetBoolean(config, "AstroTech_Environment", "Astro_Tech", false, "AstroTech Environment (y/n)");
			AstrotweaksModVariables.EnableProgressionSystem = safeGetBoolean(config, "EnableProgressionSystem", "Astro_Tech", false, "Enable Progression System (y/n)");

			ModVariables.Enable_SnowVillages = safeGetBoolean(config, "Enable_SnowVillages", "generation", ModVariables.Enable_SnowVillages, "Enable Snow Villages generation (y/n)");
			ModVariables.Enable_ForestVillages = safeGetBoolean(config, "Enable_ForestVillages", "generation", ModVariables.Enable_ForestVillages, "Enable Forest Villages generation (y/n)");
			//ModVariables.Enable_BirchVillages = safeGetBoolean(config, "Enable_BirchVillages", "generation", true, "Enable Birch Forest Villages generation (y/n)");

			ModVariables.OW_Quartz_Gen = safeGetBoolean(config, "Overworld_Quartz_Generation", "generation", ModVariables.OW_Quartz_Gen, "Enable Overworld Quartz Generation (y/n)");
			ModVariables.OW_Ruby_Gen = safeGetBoolean(config, "Ruby_Generation", "generation", ModVariables.OW_Ruby_Gen, "Enable Ruby Generation (y/n)");
			ModVariables.OW_Minerals_Gen = safeGetBoolean(config, "Overworld_Minerals_Generation", "generation", ModVariables.OW_Minerals_Gen, "Enable Overworld Minerals Generation (y/n)");
			
			
			ModVariables.Enable_Ground_Elements = safeGetBoolean(config, "Enable_Ground_Elements", "generation", ModVariables.Enable_Ground_Elements, "Enable Ground elements generation (y/n)");
			// Double values with validation of range and parsing
			ModVariables.Stick_Gen_Attempts = safeGetDouble(config, "Stick_Gen_Attempts", "generation", ModVariables.Stick_Gen_Attempts /*def*/, 0.0 /*min*/, 999.0 /*max*/, "Number of attempts to generate an element (double num) [default: 2.0]");
			String[] StickBiomes = config.get("generation", "Stick_Gen_Biomes", STICK_BIOME_LIST, 
				"List of biome IDs (e.g., minecraft:plains) where sticks generate. " + "Empty list uses default biomes.").getStringList();
			ModVariables.Stick_Gen_Min_Y = safeGetInt(config, "Stick_Gen_Min_Y", "generation", ModVariables.Stick_Gen_Min_Y, 1, 255, "Minimum Y level for stick generation (1-255)");
			ModVariables.Stick_Gen_Max_Y = safeGetInt(config, "Stick_Gen_Max_Y", "generation", ModVariables.Stick_Gen_Max_Y, 1, 255, "Maximum Y level for stick generation (1-255)");

			ModVariables.Enable_Bushes = safeGetBoolean(config, "Enable_Bushes", "generation", ModVariables.Enable_Bushes, "Enable bush generation in Overworld (y/n)");




			ModVariables.Rock_Gen_Attempts = safeGetDouble(config, "Rock_Gen_Attempts", "generation", ModVariables.Rock_Gen_Attempts /*def*/, 0.0 /*min*/, 999.0 /*max*/, "Number of attempts to generate an element (double num) [default: 0.3]");
			String[] RockBiomes = config.get("generation", "Rock_Gen_Biomes", ROCK_BIOME_LIST, 
				"List of biome IDs (e.g., minecraft:plains) where rocks generate. " + "Empty list uses default biomes.").getStringList();
			ModVariables.Rock_Gen_Min_Y = safeGetInt(config, "Rock_Gen_Min_Y", "generation", ModVariables.Rock_Gen_Min_Y, 1, 255, "Minimum Y level for rock generation (1-255)");
			ModVariables.Rock_Gen_Max_Y = safeGetInt(config, "Rock_Gen_Max_Y", "generation", ModVariables.Rock_Gen_Max_Y, 1, 255, "Maximum Y level for rock generation (1-255)");


			ModVariables.ExplosionDamageMult = safeGetDouble(config, "Explosion_Damage_Multiplier", "misc", ModVariables.ExplosionDamageMult, 0.0, 100.0, "Explosive damage modifier. (DAMAGE * Multiplier)");
			ModVariables.Extra_Fuels = safeGetBoolean(config, "Register_Extra_Fuels", "misc", ModVariables.Extra_Fuels, "Should to register more fuels for furnace? (y/n)");
			ModVariables.Better_Smelting = safeGetBoolean(config, "Better_Smelting", "misc", ModVariables.Better_Smelting, "Enable more smelting recipes? (y/n)");
			AstrotweaksModVariables.Money_Can_Smelt = safeGetBoolean(config, "Money_Can_Smelt", "misc", AstrotweaksModVariables.Money_Can_Smelt, "Can coins be melted down (y/n)");
			AstrotweaksModVariables.Money_Can_Craft = safeGetBoolean(config, "Money_Can_Craft", "misc", AstrotweaksModVariables.Money_Can_Craft, "Can copper coins be crafted at the MoneyTable from a copper plate (y/n)");
			AstrotweaksModVariables.Food_Negative_Effects = safeGetBoolean(config, "Food_Negative_Effects", "misc", AstrotweaksModVariables.Food_Negative_Effects, "Will poisonous food have more violent effects? (y/n)");
			ModVariables.Raw_Meat_Negative_Effects = safeGetBoolean(config, "Raw_Meat_Negative_Effects", "misc", ModVariables.Raw_Meat_Negative_Effects, "Will raw meat be less edible? (y/n)");
			ModVariables.doRegisterMinedBlocks = safeGetBoolean(config, "doRegisterMinedBlocks", "misc", ModVariables.doRegisterMinedBlocks, "Register mined(trapped) blocks? (y/n)");
			ModVariables.Extra_Drops_Grass = safeGetBoolean(config, "Extra_Drops_Grass", "misc", ModVariables.Extra_Drops_Grass, "Should process additional drops from grass? (y/n)");
			ModVariables.Extra_Drops_All = safeGetBoolean(config, "Extra_Drops_All", "misc", ModVariables.Extra_Drops_All, "Should extra drops be processed in general? (y/n)");

			AstrotweaksModVariables.Enable_Depths_Dimension = safeGetBoolean(config, "Enable_Depths_Dimension", "world", true, "Should register Depths dimension? (y/n)");
			AstrotweaksModVariables.Enable_Depths_Dim_Bedrock_TP = safeGetBoolean(config, "Enable_Depths_Dim_Bedrock_TP", "world", true, "Allow access to the Depths via Bedrock? (y/n)");

			ModVariables.GG_ENABLED = safeGetBoolean(config, "GG_ENABLED", "world", true, "Enable Grass growth? (y/n)");
			ModVariables.GG_MIN_DELAY_TICK = safeGetInt(config, "Grass_Growth_MIN_Delay", "world", ModVariables.GG_MIN_DELAY_TICK, 10, 1728000, "Minimum delay (ticks) for grass regrowth (10-1728000)");
			ModVariables.GG_MAX_DELAY_TICK = safeGetInt(config, "Grass_Growth_MAX_Delay", "world", ModVariables.GG_MAX_DELAY_TICK, 10, 1728000, "Maximum delay (ticks) for grass regrowth (10-1728000)");
			ModVariables.GG_MAX_OPER_PER_TICK = safeGetInt(config, "GG_MAX_PER_TICK", "world", ModVariables.GG_MAX_OPER_PER_TICK, 1, 999, "Maximum operations per tick (1-999)");
			ModVariables.GG_Density = safeGetInt(config, "Grass_Density", "world", ModVariables.GG_Density, 1, 25, "Maximum grass density (1-25)");
			


			ModVariables.QM_is_fully_unbreakable = safeGetBoolean(config, "QM_is_fully_unbreakable", "Game mechanics", ModVariables.QM_is_fully_unbreakable, "Prohibit the player from breaking the QM_block");

			ModVariables.Enable_RealisticBreak = safeGetBoolean(config, "Enable_RealisticBreak", "Game mechanics", ModVariables.Enable_RealisticBreak, "More realistic conditions for destruction of blocks");



			ModVariables.Remove_METS_engineer = safeGetBoolean(config, "Remove_METS_engineer", "mods", ModVariables.Remove_METS_engineer, "Remove trades of Engineer villager (MoreElectricTools)");
			ModVariables.Rem_Gravestone_Note = safeGetBoolean(config, "Remove_Gravestone_Note", "mods", ModVariables.Rem_Gravestone_Note, "Removes a Gravestone paper when dropped by a player");


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
			
			for (String s : StickBiomes) {
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

	private boolean safeGetBoolean(Configuration config, String name, String category, boolean def, String comment) {
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

	private double safeGetDouble(Configuration config, String name, String category, double def, double min, double max, String comment) {
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

	private int safeGetInt(Configuration config, String name, String category, int def, int min, int max, String comment) {
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
