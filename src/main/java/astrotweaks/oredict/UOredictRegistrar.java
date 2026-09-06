package astrotweaks.oredict;


import net.minecraftforge.oredict.OreDictionary;
import net.minecraft.item.ItemStack;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

import astrotweaks.item.*;
import astrotweaks.block.*;

//import net.minecraftforge.oredict.OreIngredient;



public class UOredictRegistrar {
	public UOredictRegistrar() {}

	public static void init() {
		new UOredictRegistrar();

		OreItems();
		OreBlocks();
		OreGems();
		OreDusts();
		OrePlates();
		OreNuggets();
		OreIngots();
		OreOres();
		OreRods();
	}

	//@SubscribeEvent public void preInit(FMLPreInitializationEvent event) {}

	private static void OreItems() {
		OreDictionary.registerOre("bonemeal", new ItemStack(Items.DYE, 1, 15));
		
		OreDictionary.registerOre("rock", new ItemStack(ATItems.ROCK, 1));
		OreDictionary.registerOre("rock", new ItemStack(ATItems.ROCK_FLAT, 1));
		OreDictionary.registerOre("rockFlat", new ItemStack(ATItems.ROCK_FLAT, 1));
		OreDictionary.registerOre("brickStone", new ItemStack(ATItems.STONE_BRICK, 1));
		OreDictionary.registerOre("ingotStone", new ItemStack(ATItems.STONE_BRICK, 1));
		OreDictionary.registerOre("ingotBrickStone", new ItemStack(ATItems.STONE_BRICK, 1));
		OreDictionary.registerOre("brickClay", new ItemStack(ATItems.CLAY_BRICK, 1));
		OreDictionary.registerOre("ingotClay", new ItemStack(ATItems.CLAY_BRICK, 1));
		OreDictionary.registerOre("ingotBrickClay", new ItemStack(ATItems.CLAY_BRICK, 1));
		OreDictionary.registerOre("discSilicon", new ItemStack(ATItems.SILICON_DISC, 1));
        OreDictionary.registerOre("twine", new ItemStack(ATItems.CORDAGE_FIBER, 1));
        
        OreDictionary.registerOre("singularity", new ItemStack(ATItems.NEUTRONIUM_SINGULARITY, 1));
        OreDictionary.registerOre("singularityNeutronium", new ItemStack(ATItems.NEUTRONIUM_SINGULARITY, 1));
        
        OreDictionary.registerOre("singularity", new ItemStack(ATItems.INFINITY_SINGULARITY, 1));
        OreDictionary.registerOre("singularityInfinity", new ItemStack(ATItems.INFINITY_SINGULARITY, 1));


        OreDictionary.registerOre("shardFlint", new ItemStack(ATItems.FLINT_SHARD, 1));
        OreDictionary.registerOre("shard", new ItemStack(ATItems.FLINT_SHARD, 1));
        OreDictionary.registerOre("shardBone", new ItemStack(ATItems.BONE_SHARD, 1));
        OreDictionary.registerOre("shard", new ItemStack(ATItems.BONE_SHARD, 1));
        OreDictionary.registerOre("shardDiamond", new ItemStack(ATItems.DIAMOND_SHARD, 1));
        OreDictionary.registerOre("shard", new ItemStack(ATItems.DIAMOND_SHARD, 1));

		OreDictionary.registerOre("toolSaw", new ItemStack(ItemSawIron.IRON_SAW, 1));
		OreDictionary.registerOre("toolSaw", new ItemStack(ItemSawDiamond.DIAMOND_SAW, 1));
		OreDictionary.registerOre("toolSaw", new ItemStack(ItemGoldenSaw.GOLDEN_SAW, 1));
		OreDictionary.registerOre("toolSaw", new ItemStack(ItemCopperSaw.COPPER_SAW, 1));
		OreDictionary.registerOre("toolSaw", new ItemStack(ItemBronzeSaw.BRONZE_SAW, 1));
		OreDictionary.registerOre("toolSaw", new ItemStack(ItemTinSaw.TIN_SAW, 1));
		OreDictionary.registerOre("toolSaw", new ItemStack(ItemSteelSaw.STEEL_SAW, 1));

		OreDictionary.registerOre("wireGold", new ItemStack(ATItems.COPPER_COIL, 1));
		OreDictionary.registerOre("wireCopper", new ItemStack(ATItems.GOLDEN_COIL, 1));

		OreDictionary.registerOre("listAllmeatraw", new ItemStack(Items.PORKCHOP, 1));
		OreDictionary.registerOre("listAllmeatraw", new ItemStack(Items.BEEF, 1));
		OreDictionary.registerOre("listAllmeatraw", new ItemStack(Items.RABBIT, 1));
		OreDictionary.registerOre("listAllmeatraw", new ItemStack(Items.MUTTON, 1));
		OreDictionary.registerOre("listAllmeatraw", new ItemStack(Items.CHICKEN, 1));

		OreDictionary.registerOre("listAllfishraw", new ItemStack(Items.FISH, 1));
		OreDictionary.registerOre("listAllfishraw", new ItemStack(Items.FISH, 1, 1));
		OreDictionary.registerOre("listAllfishraw", new ItemStack(Items.FISH, 1, 2));

		OreDictionary.registerOre("listAllfishcooked", new ItemStack(Items.COOKED_FISH, 1));
		OreDictionary.registerOre("listAllfishcooked", new ItemStack(ItemCoockedTropicalFish.TROPICAL_FISH, 1));
		OreDictionary.registerOre("listAllfishcooked", new ItemStack(ItemCoockedTropicalFish.TROPICAL_FISH, 1, 1));
		OreDictionary.registerOre("itemCookedFish", new ItemStack(ItemCoockedTropicalFish.TROPICAL_FISH, 1));

		OreDictionary.registerOre("listAllmilk", new ItemStack(ItemMilkBottle.MILK_BOTTLE, 1));

		OreDictionary.registerOre("banner", new ItemStack(Items.BANNER, 1));
		OreDictionary.registerOre("banner", new ItemStack(Items.BANNER, 1, 1));
		OreDictionary.registerOre("banner", new ItemStack(Items.BANNER, 1, 2));
		OreDictionary.registerOre("banner", new ItemStack(Items.BANNER, 1, 3));
		OreDictionary.registerOre("banner", new ItemStack(Items.BANNER, 1, 4));
		OreDictionary.registerOre("banner", new ItemStack(Items.BANNER, 1, 5));
		OreDictionary.registerOre("banner", new ItemStack(Items.BANNER, 1, 6));
		OreDictionary.registerOre("banner", new ItemStack(Items.BANNER, 1, 7));
		OreDictionary.registerOre("banner", new ItemStack(Items.BANNER, 1, 8));
		OreDictionary.registerOre("banner", new ItemStack(Items.BANNER, 1, 9));
		OreDictionary.registerOre("banner", new ItemStack(Items.BANNER, 1, 10));
		OreDictionary.registerOre("banner", new ItemStack(Items.BANNER, 1, 11));
		OreDictionary.registerOre("banner", new ItemStack(Items.BANNER, 1, 12));
		OreDictionary.registerOre("banner", new ItemStack(Items.BANNER, 1, 13));
		OreDictionary.registerOre("banner", new ItemStack(Items.BANNER, 1, 14));
		OreDictionary.registerOre("banner", new ItemStack(Items.BANNER, 1, 15));
	}
	private static void OreBlocks() {
		OreDictionary.registerOre("blockRuby", new ItemStack(ATBlocks.RUBY_BLOCK, 1));
		OreDictionary.registerOre("blockBrass", new ItemStack(ATBlocks.BRASS_BLOCK, 1));
		OreDictionary.registerOre("blockMineralSteel", new ItemStack(ATBlocks.MINERAL_STEEL, 1));

		OreDictionary.registerOre("stone", new ItemStack(ATBlocks.DEEPSLATE, 1));
		OreDictionary.registerOre("cobblestone", new ItemStack(ATBlocks.COBBLED_DEEPSLATE, 1));
		OreDictionary.registerOre("dirt", new ItemStack(ATBlocks.DIRT_BRICKS, 1));
		OreDictionary.registerOre("blockNetherStar", new ItemStack(ATBlocks.NETHERSTAR_BLOCK, 1));

		OreDictionary.registerOre("hardenedClay", new ItemStack(Blocks.HARDENED_CLAY, 1));
		OreDictionary.registerOre("hardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1));
		OreDictionary.registerOre("stainedHardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1));
		OreDictionary.registerOre("hardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 1));
		OreDictionary.registerOre("stainedHardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 1));
		OreDictionary.registerOre("hardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 2));
		OreDictionary.registerOre("stainedHardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 2));
		OreDictionary.registerOre("hardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 3));
		OreDictionary.registerOre("stainedHardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 3));
		OreDictionary.registerOre("hardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 4));
		OreDictionary.registerOre("stainedHardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 4));
		OreDictionary.registerOre("hardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 5));
		OreDictionary.registerOre("stainedHardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 5));
		OreDictionary.registerOre("hardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 6));
		OreDictionary.registerOre("stainedHardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 6));
		OreDictionary.registerOre("hardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 7));
		OreDictionary.registerOre("stainedHardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 7));
		OreDictionary.registerOre("hardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 8));
		OreDictionary.registerOre("stainedHardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 8));
		OreDictionary.registerOre("hardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 9));
		OreDictionary.registerOre("stainedHardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 9));
		OreDictionary.registerOre("hardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 10));
		OreDictionary.registerOre("stainedHardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 10));
		OreDictionary.registerOre("hardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 11));
		OreDictionary.registerOre("stainedHardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 11));
		OreDictionary.registerOre("hardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 12));
		OreDictionary.registerOre("stainedHardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 12));
		OreDictionary.registerOre("hardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 13));
		OreDictionary.registerOre("stainedHardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 13));
		OreDictionary.registerOre("hardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 14));
		OreDictionary.registerOre("stainedHardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 14));
		OreDictionary.registerOre("hardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 15));
		OreDictionary.registerOre("stainedHardenedClay", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 15));

		OreDictionary.registerOre("shulkerBox", new ItemStack(Blocks.WHITE_SHULKER_BOX, 1));
		OreDictionary.registerOre("shulkerBox", new ItemStack(Blocks.ORANGE_SHULKER_BOX, 1));
		OreDictionary.registerOre("shulkerBox", new ItemStack(Blocks.MAGENTA_SHULKER_BOX, 1));
		OreDictionary.registerOre("shulkerBox", new ItemStack(Blocks.LIGHT_BLUE_SHULKER_BOX, 1));
		OreDictionary.registerOre("shulkerBox", new ItemStack(Blocks.YELLOW_SHULKER_BOX, 1));
		OreDictionary.registerOre("shulkerBox", new ItemStack(Blocks.LIME_SHULKER_BOX, 1));
		OreDictionary.registerOre("shulkerBox", new ItemStack(Blocks.PINK_SHULKER_BOX, 1));
		OreDictionary.registerOre("shulkerBox", new ItemStack(Blocks.GRAY_SHULKER_BOX, 1));
		OreDictionary.registerOre("shulkerBox", new ItemStack(Blocks.SILVER_SHULKER_BOX, 1));
		OreDictionary.registerOre("shulkerBox", new ItemStack(Blocks.CYAN_SHULKER_BOX, 1));
		OreDictionary.registerOre("shulkerBox", new ItemStack(Blocks.PURPLE_SHULKER_BOX, 1));
		OreDictionary.registerOre("shulkerBox", new ItemStack(Blocks.BLUE_SHULKER_BOX, 1));
		OreDictionary.registerOre("shulkerBox", new ItemStack(Blocks.BROWN_SHULKER_BOX, 1));
		OreDictionary.registerOre("shulkerBox", new ItemStack(Blocks.GREEN_SHULKER_BOX, 1));
		OreDictionary.registerOre("shulkerBox", new ItemStack(Blocks.RED_SHULKER_BOX, 1));
		OreDictionary.registerOre("shulkerBox", new ItemStack(Blocks.BLACK_SHULKER_BOX, 1));

		/* Чета форджу не нравятся кровати с OreDict тегами -.-
		OreDictionary.registerOre("bed", new ItemStack(Blocks.BED, 1));
		OreDictionary.registerOre("bed", new ItemStack(Blocks.BED, 1, 1));
		OreDictionary.registerOre("bed", new ItemStack(Blocks.BED, 1, 2));
		OreDictionary.registerOre("bed", new ItemStack(Blocks.BED, 1, 3));
		OreDictionary.registerOre("bed", new ItemStack(Blocks.BED, 1, 4));
		OreDictionary.registerOre("bed", new ItemStack(Blocks.BED, 1, 5));
		OreDictionary.registerOre("bed", new ItemStack(Blocks.BED, 1, 6));
		OreDictionary.registerOre("bed", new ItemStack(Blocks.BED, 1, 7));
		OreDictionary.registerOre("bed", new ItemStack(Blocks.BED, 1, 8));
		OreDictionary.registerOre("bed", new ItemStack(Blocks.BED, 1, 9));
		OreDictionary.registerOre("bed", new ItemStack(Blocks.BED, 1, 10));
		OreDictionary.registerOre("bed", new ItemStack(Blocks.BED, 1, 11));
		OreDictionary.registerOre("bed", new ItemStack(Blocks.BED, 1, 12));
		OreDictionary.registerOre("bed", new ItemStack(Blocks.BED, 1, 13));
		OreDictionary.registerOre("bed", new ItemStack(Blocks.BED, 1, 14));
		OreDictionary.registerOre("bed", new ItemStack(Blocks.BED, 1, 15));
		*/

		OreDictionary.registerOre("carpet", new ItemStack(Blocks.CARPET, 1));
		OreDictionary.registerOre("carpet", new ItemStack(Blocks.CARPET, 1, 1));
		OreDictionary.registerOre("carpet", new ItemStack(Blocks.CARPET, 1, 2));
		OreDictionary.registerOre("carpet", new ItemStack(Blocks.CARPET, 1, 3));
		OreDictionary.registerOre("carpet", new ItemStack(Blocks.CARPET, 1, 4));
		OreDictionary.registerOre("carpet", new ItemStack(Blocks.CARPET, 1, 5));
		OreDictionary.registerOre("carpet", new ItemStack(Blocks.CARPET, 1, 6));
		OreDictionary.registerOre("carpet", new ItemStack(Blocks.CARPET, 1, 7));
		OreDictionary.registerOre("carpet", new ItemStack(Blocks.CARPET, 1, 8));
		OreDictionary.registerOre("carpet", new ItemStack(Blocks.CARPET, 1, 9));
		OreDictionary.registerOre("carpet", new ItemStack(Blocks.CARPET, 1, 10));
		OreDictionary.registerOre("carpet", new ItemStack(Blocks.CARPET, 1, 11));
		OreDictionary.registerOre("carpet", new ItemStack(Blocks.CARPET, 1, 12));
		OreDictionary.registerOre("carpet", new ItemStack(Blocks.CARPET, 1, 13));
		OreDictionary.registerOre("carpet", new ItemStack(Blocks.CARPET, 1, 14));
		OreDictionary.registerOre("carpet", new ItemStack(Blocks.CARPET, 1, 15));

		OreDictionary.registerOre("blockConcrete", new ItemStack(Blocks.CONCRETE, 1));
		OreDictionary.registerOre("blockConcrete", new ItemStack(Blocks.CONCRETE, 1, 1));
		OreDictionary.registerOre("blockConcrete", new ItemStack(Blocks.CONCRETE, 1, 2));
		OreDictionary.registerOre("blockConcrete", new ItemStack(Blocks.CONCRETE, 1, 3));
		OreDictionary.registerOre("blockConcrete", new ItemStack(Blocks.CONCRETE, 1, 4));
		OreDictionary.registerOre("blockConcrete", new ItemStack(Blocks.CONCRETE, 1, 5));
		OreDictionary.registerOre("blockConcrete", new ItemStack(Blocks.CONCRETE, 1, 6));
		OreDictionary.registerOre("blockConcrete", new ItemStack(Blocks.CONCRETE, 1, 7));
		OreDictionary.registerOre("blockConcrete", new ItemStack(Blocks.CONCRETE, 1, 8));
		OreDictionary.registerOre("blockConcrete", new ItemStack(Blocks.CONCRETE, 1, 9));
		OreDictionary.registerOre("blockConcrete", new ItemStack(Blocks.CONCRETE, 1, 10));
		OreDictionary.registerOre("blockConcrete", new ItemStack(Blocks.CONCRETE, 1, 11));
		OreDictionary.registerOre("blockConcrete", new ItemStack(Blocks.CONCRETE, 1, 12));
		OreDictionary.registerOre("blockConcrete", new ItemStack(Blocks.CONCRETE, 1, 13));
		OreDictionary.registerOre("blockConcrete", new ItemStack(Blocks.CONCRETE, 1, 14));
		OreDictionary.registerOre("blockConcrete", new ItemStack(Blocks.CONCRETE, 1, 15));
		OreDictionary.registerOre("blockConcretePowder", new ItemStack(Blocks.CONCRETE_POWDER, 1));
		OreDictionary.registerOre("blockConcretePowder", new ItemStack(Blocks.CONCRETE_POWDER, 1, 1));
		OreDictionary.registerOre("blockConcretePowder", new ItemStack(Blocks.CONCRETE_POWDER, 1, 2));
		OreDictionary.registerOre("blockConcretePowder", new ItemStack(Blocks.CONCRETE_POWDER, 1, 3));
		OreDictionary.registerOre("blockConcretePowder", new ItemStack(Blocks.CONCRETE_POWDER, 1, 4));
		OreDictionary.registerOre("blockConcretePowder", new ItemStack(Blocks.CONCRETE_POWDER, 1, 5));
		OreDictionary.registerOre("blockConcretePowder", new ItemStack(Blocks.CONCRETE_POWDER, 1, 6));
		OreDictionary.registerOre("blockConcretePowder", new ItemStack(Blocks.CONCRETE_POWDER, 1, 7));
		OreDictionary.registerOre("blockConcretePowder", new ItemStack(Blocks.CONCRETE_POWDER, 1, 8));
		OreDictionary.registerOre("blockConcretePowder", new ItemStack(Blocks.CONCRETE_POWDER, 1, 9));
		OreDictionary.registerOre("blockConcretePowder", new ItemStack(Blocks.CONCRETE_POWDER, 1, 10));
		OreDictionary.registerOre("blockConcretePowder", new ItemStack(Blocks.CONCRETE_POWDER, 1, 11));
		OreDictionary.registerOre("blockConcretePowder", new ItemStack(Blocks.CONCRETE_POWDER, 1, 12));
		OreDictionary.registerOre("blockConcretePowder", new ItemStack(Blocks.CONCRETE_POWDER, 1, 13));
		OreDictionary.registerOre("blockConcretePowder", new ItemStack(Blocks.CONCRETE_POWDER, 1, 14));
		OreDictionary.registerOre("blockConcretePowder", new ItemStack(Blocks.CONCRETE_POWDER, 1, 15));
		OreDictionary.registerOre("blockGlazedTerracota", new ItemStack(Blocks.WHITE_GLAZED_TERRACOTTA, 1));
		OreDictionary.registerOre("blockGlazedTerracota", new ItemStack(Blocks.ORANGE_GLAZED_TERRACOTTA, 1));
		OreDictionary.registerOre("blockGlazedTerracota", new ItemStack(Blocks.MAGENTA_GLAZED_TERRACOTTA, 1));
		OreDictionary.registerOre("blockGlazedTerracota", new ItemStack(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, 1));
		OreDictionary.registerOre("blockGlazedTerracota", new ItemStack(Blocks.YELLOW_GLAZED_TERRACOTTA, 1));
		OreDictionary.registerOre("blockGlazedTerracota", new ItemStack(Blocks.LIME_GLAZED_TERRACOTTA, 1));
		OreDictionary.registerOre("blockGlazedTerracota", new ItemStack(Blocks.PINK_GLAZED_TERRACOTTA, 1));
		OreDictionary.registerOre("blockGlazedTerracota", new ItemStack(Blocks.GRAY_GLAZED_TERRACOTTA, 1));
		OreDictionary.registerOre("blockGlazedTerracota", new ItemStack(Blocks.SILVER_GLAZED_TERRACOTTA, 1));
		OreDictionary.registerOre("blockGlazedTerracota", new ItemStack(Blocks.CYAN_GLAZED_TERRACOTTA, 1));
		OreDictionary.registerOre("blockGlazedTerracota", new ItemStack(Blocks.PURPLE_GLAZED_TERRACOTTA, 1));
		OreDictionary.registerOre("blockGlazedTerracota", new ItemStack(Blocks.BLUE_GLAZED_TERRACOTTA, 1));
		OreDictionary.registerOre("blockGlazedTerracota", new ItemStack(Blocks.BROWN_GLAZED_TERRACOTTA, 1));
		OreDictionary.registerOre("blockGlazedTerracota", new ItemStack(Blocks.GREEN_GLAZED_TERRACOTTA, 1));
		OreDictionary.registerOre("blockGlazedTerracota", new ItemStack(Blocks.RED_GLAZED_TERRACOTTA, 1));
		OreDictionary.registerOre("blockGlazedTerracota", new ItemStack(Blocks.BLACK_GLAZED_TERRACOTTA, 1));
		OreDictionary.registerOre("blockMossy", new ItemStack(Blocks.MOSSY_COBBLESTONE, 1));

		OreDictionary.registerOre("blockDirt", new ItemStack(Blocks.DIRT, 1, 1));

		OreDictionary.registerOre("blockGrass", new ItemStack(Blocks.DIRT, 1, 2));


		//OreDictionary.registerOre("cobblestoneSlab", new ItemStack(BlockCobbledDeepslateSlab.block, 1));
	}
	private static void OreGems() {
        OreDictionary.registerOre("gemRuby", new ItemStack(ATItems.RUBY, 1));
	}
	private static void OreDusts() {
        OreDictionary.registerOre("dustRuby", new ItemStack(ATItems.RUBY_DUST, 1));
        OreDictionary.registerOre("dustCement", new ItemStack(ATItems.CEMENT_DUST, 1));
        OreDictionary.registerOre("dustBrass", new ItemStack(ATItems.BRASS_DUST, 1));
        OreDictionary.registerOre("dustMineralSteel", new ItemStack(ATItems.MINERAL_STEEL_DUST, 1));
	}
	private static void OrePlates() {

	}
    private static void OreNuggets() {
        OreDictionary.registerOre("nuggetBrass", new ItemStack(ATItems.BRASS_NUGGET, 1));
    }
    private static void OreIngots() {
        OreDictionary.registerOre("ingotBrass", new ItemStack(ATItems.BRASS_INGOT, 1));
        OreDictionary.registerOre("ingotMineralSteel", new ItemStack(ATItems.MINERAL_STEEL_INGOT, 1));
	}
	private static void OreOres() {
		OreDictionary.registerOre("oreRuby", new ItemStack(BlockRubyOre.block, 1));
		OreDictionary.registerOre("oreQuartz", new ItemStack(BlockQuartzOreStone.block, 1));
		OreDictionary.registerOre("oreQuartz", new ItemStack(BlockQuartzOreGranite.block, 1));
	}
	///
	private static void OreRods() {
		OreDictionary.registerOre("rodIron", new ItemStack(ATItems.IRON_STICK, 1));
        OreDictionary.registerOre("rodGold", new ItemStack(ATItems.GOLD_STICK, 1));
        OreDictionary.registerOre("rodCopper", new ItemStack(ATItems.COPPER_STICK, 1));
        OreDictionary.registerOre("rodTin", new ItemStack(ATItems.TIN_STICK, 1));
        OreDictionary.registerOre("rodBronze", new ItemStack(ATItems.BRONZE_STICK, 1));
        OreDictionary.registerOre("rodDiamond", new ItemStack(ATItems.DIAMOND_STICK, 1));
        OreDictionary.registerOre("rodAluminium", new ItemStack(ATItems.ALUMINIUM_STICK, 1));
        OreDictionary.registerOre("rodTitanium", new ItemStack(ATItems.TITANIUM_STICK, 1));
        OreDictionary.registerOre("rodNickel", new ItemStack(ATItems.NICKEL_STICK, 1));
        OreDictionary.registerOre("rodCobalt", new ItemStack(ATItems.COBALT_STICK, 1));
        OreDictionary.registerOre("rodMeteoricIron", new ItemStack(ATItems.METEORIC_STICK, 1));
        OreDictionary.registerOre("rodElectrum", new ItemStack(ATItems.ELECTRUM_STICK, 1));
        OreDictionary.registerOre("rodEmerald", new ItemStack(ATItems.EMERALD_STICK, 1));
        OreDictionary.registerOre("rodRuby", new ItemStack(ATItems.RUBY_STICK, 1));
        OreDictionary.registerOre("rodSteel", new ItemStack(ATItems.STEEL_STICK, 1));
        OreDictionary.registerOre("rodIridium", new ItemStack(ATItems.IRIDIUM_STICK, 1));
        OreDictionary.registerOre("rodSilver", new ItemStack(ATItems.SILVER_STICK, 1));
        OreDictionary.registerOre("rodUranium", new ItemStack(ATItems.URANIUM_STICK, 1));
        OreDictionary.registerOre("rodBrass", new ItemStack(ATItems.BRASS_STICK, 1));
        OreDictionary.registerOre("rodCarbon", new ItemStack(ATItems.CARBON_STICK, 1));

	}
}
