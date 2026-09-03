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
		
		OreDictionary.registerOre("rock", new ItemStack(ItemRock.block, 1));
		OreDictionary.registerOre("rock", new ItemStack(ItemRockFlat.block, 1));
		OreDictionary.registerOre("rockFlat", new ItemStack(ItemRockFlat.block, 1));
		OreDictionary.registerOre("brickStone", new ItemStack(ItemStoneBrick.block, 1));
		OreDictionary.registerOre("ingotStone", new ItemStack(ItemStoneBrick.block, 1));
		OreDictionary.registerOre("ingotBrickStone", new ItemStack(ItemStoneBrick.block, 1));
		OreDictionary.registerOre("brickClay", new ItemStack(ItemClayBrick.block, 1));
		OreDictionary.registerOre("ingotClay", new ItemStack(ItemClayBrick.block, 1));
		OreDictionary.registerOre("ingotBrickClay", new ItemStack(ItemClayBrick.block, 1));
		OreDictionary.registerOre("discSilicon", new ItemStack(ItemSiliconDisc.block, 1));
		OreDictionary.registerOre("twine", new ItemStack(ItemCordageFiber.block, 1));
		
		OreDictionary.registerOre("singularity", new ItemStack(ItemNeutroniumsingularity.block, 1));
		OreDictionary.registerOre("singularityNeutronium", new ItemStack(ItemNeutroniumsingularity.block, 1));
		
		OreDictionary.registerOre("singularity", new ItemStack(ItemInfinitySingularity.block, 1));
		OreDictionary.registerOre("singularityInfinity", new ItemStack(ItemInfinitySingularity.block, 1));

		OreDictionary.registerOre("shardFlint", new ItemStack(ItemFlintShard.block, 1));
		OreDictionary.registerOre("shard", new ItemStack(ItemFlintShard.block, 1));
		OreDictionary.registerOre("shardBone", new ItemStack(ItemBoneShard.block, 1));
		OreDictionary.registerOre("shard", new ItemStack(ItemBoneShard.block, 1));
		OreDictionary.registerOre("shardDiamond", new ItemStack(ItemDiamondShard.block, 1));
		OreDictionary.registerOre("shard", new ItemStack(ItemDiamondShard.block, 1));

		OreDictionary.registerOre("toolSaw", new ItemStack(ItemSawIron.block, 1));
		OreDictionary.registerOre("toolSaw", new ItemStack(ItemSawDiamond.block, 1));
		OreDictionary.registerOre("toolSaw", new ItemStack(ItemGoldenSaw.block, 1));
		OreDictionary.registerOre("toolSaw", new ItemStack(ItemCopperSaw.block, 1));
		OreDictionary.registerOre("toolSaw", new ItemStack(ItemBronzeSaw.block, 1));
		OreDictionary.registerOre("toolSaw", new ItemStack(ItemTinSaw.block, 1));
		OreDictionary.registerOre("toolSaw", new ItemStack(ItemSteelSaw.block, 1));

		OreDictionary.registerOre("wireGold", new ItemStack(ItemInductanceCoil1.block, 1));
		OreDictionary.registerOre("wireCopper", new ItemStack(ItemInductanceCoil2.block, 1));

		OreDictionary.registerOre("listAllmeatraw", new ItemStack(Items.PORKCHOP, 1));
		OreDictionary.registerOre("listAllmeatraw", new ItemStack(Items.BEEF, 1));
		OreDictionary.registerOre("listAllmeatraw", new ItemStack(Items.RABBIT, 1));
		OreDictionary.registerOre("listAllmeatraw", new ItemStack(Items.MUTTON, 1));
		OreDictionary.registerOre("listAllmeatraw", new ItemStack(Items.CHICKEN, 1));

		OreDictionary.registerOre("listAllfishraw", new ItemStack(Items.FISH, 1));
		OreDictionary.registerOre("listAllfishraw", new ItemStack(Items.FISH, 1, 1));
		OreDictionary.registerOre("listAllfishraw", new ItemStack(Items.FISH, 1, 2));

		OreDictionary.registerOre("listAllfishcooked", new ItemStack(Items.COOKED_FISH, 1));
		OreDictionary.registerOre("listAllfishcooked", new ItemStack(ItemCoockedTropicalFish.block, 1));
		OreDictionary.registerOre("listAllfishcooked", new ItemStack(ItemCoockedTropicalFish.block, 1, 1));
		OreDictionary.registerOre("itemCookedFish", new ItemStack(ItemCoockedTropicalFish.block, 1));

		OreDictionary.registerOre("listAllmilk", new ItemStack(ItemMilkBottle.block, 1));

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
		OreDictionary.registerOre("blockRuby", new ItemStack(BlockRubyBlock.block, 1));
		OreDictionary.registerOre("blockBrass", new ItemStack(BlockBrassBlock.block, 1));
		OreDictionary.registerOre("blockMineralSteel", new ItemStack(BlockMineralSteel.block, 1));

		OreDictionary.registerOre("stone", new ItemStack(BlockDeepslate.block, 1));
		OreDictionary.registerOre("cobblestone", new ItemStack(BlockCobbledDeepslate.block, 1));
		OreDictionary.registerOre("dirt", new ItemStack(BlockDirtBricks.block, 1));
		OreDictionary.registerOre("blockNetherStar", new ItemStack(BlockNetherstarBlock.block, 1));

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
		OreDictionary.registerOre("gemRuby", new ItemStack(ItemRuby.block, 1));
	}
	private static void OreDusts() {
		OreDictionary.registerOre("dustRuby", new ItemStack(ItemRubyDust.block, 1));
		OreDictionary.registerOre("dustCement", new ItemStack(ItemCementDust.block, 1));
		OreDictionary.registerOre("dustBrass", new ItemStack(ItemBrassDust.block, 1));
		OreDictionary.registerOre("dustMineralSteel", new ItemStack(ItemMineralSteelDust.block, 1));
	}
	private static void OrePlates() {

	}
	private static void OreNuggets() {
		OreDictionary.registerOre("nuggetBrass", new ItemStack(ItemBrassNugget.block, 1));
	}
	private static void OreIngots() {
		OreDictionary.registerOre("ingotBrass", new ItemStack(ItemBrassIngot.block, 1));
		OreDictionary.registerOre("ingotMineralSteel", new ItemStack(ItemMineralSteelIngot.block, 1));
	}
	private static void OreOres() {
		OreDictionary.registerOre("oreRuby", new ItemStack(BlockRubyOre.block, 1));
		OreDictionary.registerOre("oreQuartz", new ItemStack(BlockQuartzOreStone.block, 1));
		OreDictionary.registerOre("oreQuartz", new ItemStack(BlockQuartzOreGranite.block, 1));
	}
	///
	private static void OreRods() {
		OreDictionary.registerOre("rodIron", new ItemStack(ItemIronStick.block, 1));
		OreDictionary.registerOre("rodGold", new ItemStack(ItemGoldStick.block, 1));
		OreDictionary.registerOre("rodCopper", new ItemStack(ItemCopperStick.block, 1));
		OreDictionary.registerOre("rodTin", new ItemStack(ItemTinStick.block, 1));
		OreDictionary.registerOre("rodBronze", new ItemStack(ItemBronzeStick.block, 1));
		OreDictionary.registerOre("rodDiamond", new ItemStack(ItemDiamondStick.block, 1));
	    OreDictionary.registerOre("rodAluminium", new ItemStack(ItemAluminiumStick.block, 1));
	    OreDictionary.registerOre("rodTitanium", new ItemStack(ItemTitaniumStick.block, 1));
	    OreDictionary.registerOre("rodNickel", new ItemStack(ItemNickelStick.block, 1));
	    OreDictionary.registerOre("rodCobalt", new ItemStack(ItemCobaltStick.block, 1));
	    OreDictionary.registerOre("rodMeteoricIron", new ItemStack(ItemMeteoricStick.block, 1));
	    OreDictionary.registerOre("rodElectrum", new ItemStack(ItemElectrumStick.block, 1));
	    OreDictionary.registerOre("rodEmerald", new ItemStack(ItemEmeraldStick.block, 1));
	    OreDictionary.registerOre("rodRuby", new ItemStack(ItemRubyStick.block, 1));
	    OreDictionary.registerOre("rodSteel", new ItemStack(ItemSteelStick.block, 1));
	    OreDictionary.registerOre("rodIridium", new ItemStack(ItemIridiumStick.block, 1));
	    OreDictionary.registerOre("rodSilver", new ItemStack(ItemSilverStick.block, 1));
	    OreDictionary.registerOre("rodUranium", new ItemStack(ItemUraniumStick.block, 1));
	    OreDictionary.registerOre("rodBrass", new ItemStack(ItemBrassStick.block, 1));
		OreDictionary.registerOre("rodCarbon", new ItemStack(ItemCarbonStick.block, 1));
	}
}
