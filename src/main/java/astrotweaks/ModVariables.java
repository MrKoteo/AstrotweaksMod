package astrotweaks;


import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import net.minecraftforge.oredict.OreDictionary;
//import net.minecraft.block.Block;
//import net.minecraft.world.World;


import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import java.util.*;



public class ModVariables {
	public ModVariables() {}
	public static boolean EnableProgressionSystem = false;
	public static boolean AstroTech_Environment = false;


	//								   МИНУТ * сек * тик
	public static int GG_MIN_DELAY_TICK = 5 * 60 * 20; // 5 minutes
	public static int GG_MAX_DELAY_TICK = 15 * 60 * 20; // 15 minutes
	public static int GG_MAX_OPER_PER_TICK = 16;
	public static int GG_Density = 14; // blocks in area
	public static int GG_Tall_Density = 9;
	public static boolean GG_ENABLED = true;
	//public static BitSet GGBlacklist;

	public static boolean Remove_METS_engineer = false;
	public static boolean Rem_Gravestone_Note = true;

	public static final boolean ServerPingFix = true;

	public static boolean Enable_RealisticBreak = false;
	public static double ExplosionDamageMult = 1.5D;

	public static boolean QM_is_fully_unbreakable = true;
	public static boolean Better_Smelting = true;
	public static boolean Extra_Drops_Grass = true;
	public static boolean Extra_Drops_All = true;

	public static boolean Raw_Meat_Negative_Effects = true;
	public static boolean Food_Negative_Effects = true;

	public static boolean Enable_Depths_Dimension = true;
	public static boolean Enable_Depths_Dim_Bedrock_TP = true;

	public static boolean Enable_SnowVillages = true;
	public static boolean Enable_ForestVillages = true;
	//public static boolean Enable_BirchVillages = true;

	public static boolean Enable_Ground_Elements = true;
	public static double Stick_Gen_Attempts = 2.0D;
	public static double Rock_Gen_Attempts = 0.4D;
	public static int Stick_Gen_Min_Y = 60;
	public static int Stick_Gen_Max_Y = 125;
	public static int Rock_Gen_Min_Y = 55;
	public static int Rock_Gen_Max_Y = 150;
	public static Set<ResourceLocation> Stick_Gen_Biomes = null;
	public static Set<ResourceLocation> Rock_Gen_Biomes  = null;
	public static Set<Biome> Stick_Gen_Biomes_Cached = null;
	public static Set<Biome> Rock_Gen_Biomes_Cached = null;

	public static boolean Enable_Bushes = true;
	public static boolean Extra_Fuels = true;
	public static boolean doRegisterMinedBlocks = true;

	public static boolean Money_Can_Smelt = true;
	public static boolean Money_Can_Craft = true;
	public static boolean Money_Can_Conversion = true;
	public static int Money_ConvCount = 10;

	public static boolean OW_Minerals_Gen = true;
	public static boolean OW_Ruby_Gen = true;
	public static boolean OW_Quartz_Gen = true;

	public static int QTS_Max_Range = 8192;


	/// TEH

	public static final Set<Biome> GEN_DEFAULT_BIOMES = createDefaultBiomes();
	private static final Set<Biome> createDefaultBiomes() {
	    ResourceLocation[] names = new ResourceLocation[] {
	        new ResourceLocation("plains"), new ResourceLocation("forest"), new ResourceLocation("taiga"),
	        new ResourceLocation("swampland"), new ResourceLocation("forest_hills"), new ResourceLocation("taiga_hills"),
	        new ResourceLocation("jungle"), new ResourceLocation("jungle_hills"), new ResourceLocation("jungle_edge"),
	        new ResourceLocation("birch_forest"), new ResourceLocation("birch_forest_hills"), new ResourceLocation("roofed_forest"),
	        new ResourceLocation("redwood_taiga"), new ResourceLocation("redwood_taiga_hills"), new ResourceLocation("savanna"),
	        new ResourceLocation("river"), new ResourceLocation("smaller_extreme_hills"),
	        new ResourceLocation("extreme_hills_with_trees"), new ResourceLocation("savanna_rock")
	    };
	
	    Set<Biome> set = new HashSet<>(names.length);
	    for (ResourceLocation rl : names) {
	        Biome b = Biome.REGISTRY.getObject(rl);
	        if (b != null) set.add(b);
	    }
	    return Collections.unmodifiableSet(set);
	}

	//public static Set<Biome> GGAllowed = createGGBiomes();


	public static List<ItemStack> MEAT_LIST;

	//##################################################

	public static void preInit(FMLPreInitializationEvent event) {
	}
	public static void init() {

		

        List<ItemStack> items = new ArrayList<>();
		String[] oreDictNames = {
		    "listAllmeatraw",
		    "meatRaw",
		    "listAllfishraw"
		};
		for (String name : oreDictNames) {
		    for (ItemStack stack : OreDictionary.getOres(name)) {
		        if (stack != null && !stack.isEmpty()) {
		            items.add(stack.copy());
		        }
		    }
		}

        String[] mEntries = {
            "minecraft:fish:1",
            "minecraft:fish:2"
        };
        for (String entry : mEntries) {
            String[] parts = entry.split(":");
            if (parts.length < 2) continue;
            String modid = parts[0];
            String itemName = parts[1];
            int meta = (parts.length > 2) ? Integer.parseInt(parts[2]) : 0;
            Item item = Item.getByNameOrId(modid + ":" + itemName);
            if (item != null) {
                ItemStack stack = new ItemStack(item, 1, meta);
                items.add(stack);
            } else {
                System.out.println("Couldn't find item: " + entry);
            }
        }
        
        MEAT_LIST = Collections.unmodifiableList(items);

		//public static volatile Set<Block> DIRT_LIKE = Collections.emptySet();
	}

	public static void postInit() {

		/*
	    ResourceLocation[] names = new ResourceLocation[] {
	        new ResourceLocation("desert"), new ResourceLocation("frozen_ocean"), new ResourceLocation("frozen_river"), new ResourceLocation("ice_flats"),
	        new ResourceLocation("ice_mountains"), new ResourceLocation("mushroom_island"), new ResourceLocation("mushroom_island_shore"), new ResourceLocation("desert_hills"),
	        new ResourceLocation("cold_beach"), new ResourceLocation("taiga_cold"), new ResourceLocation("taiga_cold_hills"), new ResourceLocation("mesa"),
	        new ResourceLocation("mesa_rock"), new ResourceLocation("mesa_clear_rock"), new ResourceLocation("mutated_desert"), new ResourceLocation("mutated_ice_flats"),
	        new ResourceLocation("mutated_mesa"), new ResourceLocation("mutated_mesa_rock"), new ResourceLocation("mutated_mesa_clear_rock")
	    };
	
	    Set<Biome> set = new HashSet<>(names.length);
	    for (ResourceLocation rl : names) {
	        Biome b = Biome.REGISTRY.getObject(rl);
	        if (b != null) set.add(b);
	    }
	    GGBlacklist = Collections.unmodifiableSet(set);
	    */
		String[] names = new String[] {
		    "desert","frozen_ocean","frozen_river","ice_flats","ice_mountains",
		    "mushroom_island","mushroom_island_shore","desert_hills","cold_beach",
		    "taiga_cold","taiga_cold_hills","mesa","mesa_rock","mesa_clear_rock",
		    "mutated_desert","mutated_ice_flats","mutated_mesa","mutated_mesa_rock","mutated_mesa_clear_rock"
		};
		
		int maxId = 0;
		// Find MAX ID in registry (BitSet size)
		for (int i = 0; i < Biome.REGISTRY.getKeys().size(); i++) {
		    maxId = Math.max(maxId, Biome.REGISTRY.getKeys().size());
		}
		BitSet biomeBlacklistBits = new BitSet(maxId + 1);
		for (String s : names) {
		    ResourceLocation rl = new ResourceLocation(s);
		    Biome b = Biome.REGISTRY.getObject(rl);
		    if (b != null) {
		        int id = Biome.REGISTRY.getIDForObject(b);
		        if (id >= 0) biomeBlacklistBits.set(id);
		    }
		}

		//GGBlacklist = biomeBlacklistBits;
	

	
	}
}
