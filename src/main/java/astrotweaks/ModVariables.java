package astrotweaks;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.oredict.OreDictionary;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.client.Minecraft;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import java.util.*;



public class ModVariables {
	public ModVariables() {}

	public static boolean Remove_METS_engineer = false;


	public static boolean Enable_RealisticBreak = false;
	public static double ExplosionDamageMult = 1.5D;

	public static boolean QM_is_fully_unbreakable = true;
	public static boolean Better_Smelting = true;

	public static boolean Raw_Meat_Negative_Effects = true;

	public static boolean Enable_SnowVillages = true;
	public static boolean Enable_ForestVillages = true;
	//public static boolean Enable_BirchVillages = true;

	public static boolean Enable_Ground_Elements = true;
	public static double Stick_Gen_Attempts = 1.9D;
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

	public static boolean OW_Minerals_Gen = true;
	public static boolean OW_Ruby_Gen = true;
	public static boolean OW_Quartz_Gen = true;


	/// TEH

	public static final Set<Biome> GEN_DEFAULT_BIOMES = createDefaultBiomes();
	private static Set<Biome> createDefaultBiomes() {
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

	public static List<ItemStack> MEAT_LIST;

	//##################################################

	public void preInit(FMLPreInitializationEvent event) {
	}
	public void init() {
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
            "galacticraftcore:food:6",
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
}
