package astrotweaks.block;

import astrotweaks.creativetab.ATCreativeTabs;
import astrotweaks.item.ATItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rotation;
import net.minecraft.util.Mirror;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;



@Mod.EventBusSubscriber(modid = "astrotweaks")
public class ATBlocks {
    public static final String MOD_ID = "astrotweaks";



    public static final Block BEETROOT_BLOCK = new BlockBeetroot();
    public static final Block HAZARD_ZONE_TAPE = new BlockHazardZoneTape();
    public static final Block AISLE_CLEANING_TAPE = new BlockAisleCleaning();
    public static final Block APPLE_BLOCK = new BlockAppleBlock();
    public static final Block BRASS_BLOCK = new BlockBrassBlock();
    public static final Block BREAD_BLOCK = new BlockBreadBlock();
    public static final Block CARROT_BLOCK = new BlockCarrotBlock();
    public static final Block HAZARD_TAPE = new BlockCautionHazard();
    public static final Block COBBLED_DEEPSLATE = new BlockCobbledDeepslate();
    public static final Block COMPRESSED_BRONZE_BLOCK = new BlockCompressedBronzeBlock();
    public static final Block COMPRESSED_COAL_BLOCK = new BlockCompressedCoalBlock();
    public static final Block COMPRESSED_COBBLESTONE = new BlockCompressedCobblestone();
    public static final Block COMPRESSED_COPPER_BLOCK = new BlockCompressedCopperBlock();
    public static final Block COMPRESSED_DIAMOND_BLOCK = new BlockCompressedDiamondBlock();
    public static final Block COMPRESSED_EMERALD_BLOCK = new BlockCompressedEmeraldBlock();
    public static final Block COMPRESSED_ENDSTONE = new BlockCompressedEndstone();
    public static final Block COMPRESSED_GOLD_BLOCK = new BlockCompressedGoldBlock();
    public static final Block COMPRESSED_IRON_BLOCK = new BlockCompressedIronBlock();
    public static final Block COMPRESSED_LAPIS_BLOCK = new BlockCompressedLapisBlock();
    public static final Block COMPRESSED_LEAD_BLOCK = new BlockCompressedLeadBlock();
    public static final Block COMPRESSED_QUARTZ = new BlockCompressedQuartz();
    public static final Block COMPRESSED_REDSTONE_BLOCK = new BlockCompressedRedstoneBlock();
    public static final Block COMPRESSED_SAND = new BlockCompressedSand();
    public static final Block COMPRESSED_STEEL_BLOCK = new BlockCompressedSteelBlock();
    public static final Block COMPRESSED_TIN_BLOCK = new BlockCompressedTinBlock();
    public static final Block DECO_BLOCK_1 = new BlockDecoBlock1();
    public static final Block DECO_BLOCK_GRID = new BlockDecoBlockGrid();
    public static final Block DEEP_DIAMOND_ORE = new BlockDeepDiamondOre();
    public static final Block DEEP_EMERALD_ORE = new BlockDeepEmeraldOre();
    public static final Block DEEP_GOLD_ORE = new BlockDeepGoldOre();
    public static final Block DEEP_IRON_ORE = new BlockDeepIronOre();
    public static final Block DEEP_LAPIS_ORE = new BlockDeepLapisOre();
    public static final Block DEEP_REDSTONE_ORE = new BlockDeepRedstoneOre();
    public static final Block DEEPSLATE_BRICKS = new BlockDeepslateBricks();
    public static final Block DEEPSLATE_BRICKS_STAIRS = new BlockDeepslateBricksStairs();
    public static final Block DEEPSLATE_TILES = new BlockDeepslateTiles();
    public static final Block DEEPSLATE_TILES_STAIRS = new BlockDeepslateTilesStairs();
    public static final Block DIRT_BRICKS = new BlockDirtBricks();
    public static final Block DONT_WALK_TAPE = new BlockDontWalk();
    public static final Block DOUBLE_COMPRESSED_QUARTZ = new BlockDoubleCompressedQuartz();
    public static final Block METAL_FRAME = new BlockMetalFrameBW();
    public static final Block MINERAL_STEEL = new BlockMineralSteel();
    public static final Block NETHERSTAR_BLOCK = new BlockNetherstarBlock();
    public static final Block NULL_BLOCK = new BlockNullBlockx();
    public static final Block POTATO_BLOCK = new BlockPotatoBlock();
    public static final Block RADIATION_HAZARD_TAPE = new BlockRadiationHazard();
    public static final Block RUBY_BLOCK = new BlockRubyBlock();
    public static final Block SAFETY_ZONE_TAPE = new BlockSafetyZone();
    public static final Block SUGAR_BLOCK = new BlockSugarBlock();
    public static final Block TECHNICAL_WORKS_TAPE = new BlockTechnicalWorks();
    public static final Block TRIPLE_COMPRESSED_QUARTZ = new BlockTripleCompressedQuartz();
    public static final Block DEEPSLATE = new BlockDeepslate();

    public static final Block FEXPLOSIVE = new BlockFexplosive();




// -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

    private ATBlocks() {}

    private static final Block[] BLOCKS_TO_REGISTER = {




APPLE_BLOCK,BEETROOT_BLOCK,CARROT_BLOCK,POTATO_BLOCK,BREAD_BLOCK,SUGAR_BLOCK,

DIRT_BRICKS,




BRASS_BLOCK,


DEEPSLATE,COBBLED_DEEPSLATE,DEEPSLATE_BRICKS,DEEPSLATE_BRICKS_STAIRS,DEEPSLATE_TILES,DEEPSLATE_TILES_STAIRS,
DEEP_DIAMOND_ORE,DEEP_EMERALD_ORE,DEEP_GOLD_ORE,DEEP_IRON_ORE,DEEP_LAPIS_ORE,DEEP_REDSTONE_ORE,





COMPRESSED_BRONZE_BLOCK,COMPRESSED_COAL_BLOCK,COMPRESSED_COBBLESTONE,COMPRESSED_COPPER_BLOCK,COMPRESSED_DIAMOND_BLOCK,COMPRESSED_EMERALD_BLOCK,COMPRESSED_ENDSTONE,COMPRESSED_GOLD_BLOCK,
COMPRESSED_IRON_BLOCK,COMPRESSED_LAPIS_BLOCK,COMPRESSED_LEAD_BLOCK,COMPRESSED_QUARTZ,COMPRESSED_REDSTONE_BLOCK,COMPRESSED_SAND,COMPRESSED_STEEL_BLOCK,COMPRESSED_TIN_BLOCK,
DOUBLE_COMPRESSED_QUARTZ,TRIPLE_COMPRESSED_QUARTZ,


HAZARD_TAPE,HAZARD_ZONE_TAPE,TECHNICAL_WORKS_TAPE,DONT_WALK_TAPE,RADIATION_HAZARD_TAPE,SAFETY_ZONE_TAPE,AISLE_CLEANING_TAPE,
DECO_BLOCK_1,DECO_BLOCK_GRID,METAL_FRAME,
















MINERAL_STEEL,RUBY_BLOCK,

FEXPLOSIVE,
NETHERSTAR_BLOCK,
NULL_BLOCK,








// Ссылки писать ниже:

BlockRubyOre.block,
BlockMineralsOre.block,
BlockMine.block,
BlockCobbledDeepslateSlab.block,
BlockCobbledDeepslateSlab.block_slab_double,
BlockCobbledDeepslateStairs.block,
BlockDeepMinerals.block,
BlockDeepRichMinerals.block,
BlockDeepslateBricksSlab.block,
BlockDeepslateBricksSlab.block_slab_double,
BlockDeepslateTilesSlab.block,
BlockDeepslateTilesSlab.block_slab_double,
BlockBush1.block,
BlockBush2.block,
BlockBush3.block,
BlockBush4.block,
BlockBush5.block,
BlockBush6.block,
BlockBush7.block,
BlockFern1.block,
BlockGiantGrass.block,
BlockGroundRock1.block,
BlockGroundRock2.block,
BlockGroundStick.block,
BlockHeavyMine.block,
BlockQmBlock.block,
BlockQuartzOreGranite.block,
BlockQuartzOreStone.block,
BlockRailMine.block,
BlockRedMushrooms.block,
BlockBrownMushrooms.block,
BlockUDestroyerBlock.block,
BlockUKillerBlock.block,
BlockUnknownBlock.block,




    };

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(BLOCKS_TO_REGISTER);
    }
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        for (Block blk : BLOCKS_TO_REGISTER) {
            if (blk instanceof BlockSlab) {
                BlockSlab slab = (BlockSlab) blk;
                if (slab.isDouble()) continue;
                ResourceLocation rl = blk.getRegistryName();
                Block doubleSlab = Block.REGISTRY.getObject(new ResourceLocation(rl.getResourceDomain(), rl.getResourcePath() + "_double"));
                event.getRegistry().register(new ItemSlab(blk, slab, (BlockSlab) doubleSlab).setRegistryName(rl));
                continue;
            }
            event.getRegistry().register( new ItemBlock(blk).setRegistryName(blk.getRegistryName()) );
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, value = net.minecraftforge.fml.relauncher.Side.CLIENT)
    public static class ClientHandler {
        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {
            for (Block block : BLOCKS_TO_REGISTER) {
                Item item = Item.getItemFromBlock(block);
                if (item == Items.AIR) continue;
                ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(block.getRegistryName(),"inventory")
                );
            }
        }
    }


// -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-


    public static class BlockBeetroot extends BlockFalling {
        public BlockBeetroot() {
            super(Material.SAND);
            setRegistryName(MOD_ID, "beetroot_block");
            setUnlocalizedName("beetroot_block");
            setSoundType(SoundType.WOOD);
            setHarvestLevel("shovel", 0);
            setHardness(2.1F);
            setResistance(7.0F);
            setLightOpacity(255);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        // @Override с одним параметром можно ужать в одну строку. Если строк в @Overridе несколько, пишем как есть.
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.PURPLE_STAINED_HARDENED_CLAY; }
    }
    public static class BlockHazardZoneTape extends Block {
        public BlockHazardZoneTape() {
            super(Material.IRON);
            setRegistryName(MOD_ID, "hazard_zone_tape");
            setUnlocalizedName("hazard_zone_tape");
            setSoundType(SoundType.METAL);
            setHarvestLevel("pickaxe", 1);
            setHardness(3.0F);
            setResistance(30.0F);
            setLightOpacity(255);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.YELLOW; }
    }
    public static class BlockAisleCleaning extends Block {
        public BlockAisleCleaning() {
            super(Material.IRON);
            setRegistryName(MOD_ID, "aisle_cleaning_tape");
            setUnlocalizedName("aisle_cleaning_tape");
            setSoundType(SoundType.METAL);
            setHarvestLevel("pickaxe", 1);
            setHardness(3F);
            setResistance(25F);
            setLightOpacity(255);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.QUARTZ; }
    }
    public static class BlockAppleBlock extends BlockFalling {
        public BlockAppleBlock() {
            super(Material.SAND);
            setRegistryName(MOD_ID, "apple_block");
            setUnlocalizedName("apple_block");
            setSoundType(SoundType.WOOD);
            setHarvestLevel("shovel", 0);
            setHardness(2.1999999999999997F);
            setResistance(6F);
            setLightOpacity(255);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.RED; }
    }
    public static class BlockBrassBlock extends Block {
        public BlockBrassBlock() {
            super(Material.IRON);
            setRegistryName(MOD_ID, "brass_block");
            setUnlocalizedName("brass_block");
            setSoundType(SoundType.METAL);
            setHarvestLevel("pickaxe", 1);
            setHardness(5F);
            setResistance(20F);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.YELLOW_STAINED_HARDENED_CLAY; }
    }
    public static class BlockBreadBlock extends BlockFalling {
        public BlockBreadBlock() {
            super(Material.SAND);
            setRegistryName(MOD_ID, "bread_block");
            setUnlocalizedName("bread_block");
            setSoundType(SoundType.WOOD);
            setHarvestLevel("shovel", 0);
            setHardness(2.4F);
            setResistance(4.5F);
            setLightOpacity(255);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.BROWN; }
    }
    public static class BlockCarrotBlock extends BlockFalling {
        public BlockCarrotBlock() {
            super(Material.SAND);
            setRegistryName(MOD_ID, "carrot_block");
            setUnlocalizedName("carrot_block");
            setSoundType(SoundType.WOOD);
            setHarvestLevel("shovel", 0);
            setHardness(2.3F);
            setResistance(6.5F);
            setLightOpacity(255);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.ORANGE_STAINED_HARDENED_CLAY; }
    }
    public static class BlockCautionHazard extends Block {
        public BlockCautionHazard() {
            super(Material.IRON);
            setRegistryName(MOD_ID, "hazard_tape");
            setUnlocalizedName("hazard_tape");
            setSoundType(SoundType.METAL);
            setHarvestLevel("pickaxe", 1);
            setHardness(3F);
            setResistance(25F);
            setLightOpacity(255);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.GOLD; }
    }
    public static class BlockCobbledDeepslate extends Block {
        public BlockCobbledDeepslate() {
            super(Material.ROCK);
            setRegistryName(MOD_ID, "cobbled_deepslate");
            setUnlocalizedName("cobbled_deepslate");
            setSoundType(SoundType.STONE);
            setHarvestLevel("pickaxe", 0);
            setHardness(3.5F);
            setResistance(12F);
            setLightOpacity(255);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
    }
    public static class BlockCompressedBronzeBlock extends Block {
        public BlockCompressedBronzeBlock() {
            super(Material.IRON);
            setRegistryName(MOD_ID, "compressed_bronze_block");
            setUnlocalizedName("compressed_bronze_block");
            setSoundType(SoundType.METAL);
            setHarvestLevel("pickaxe", 2);
            setHardness(11F);
            setResistance(40F);
            setLightOpacity(255);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.ORANGE_STAINED_HARDENED_CLAY; }
    }
    public static class BlockCompressedCoalBlock extends Block {
        public BlockCompressedCoalBlock() {
            super(Material.ROCK);
            setRegistryName(MOD_ID, "compressed_coal_block");
            setUnlocalizedName("compressed_coal_block");
            setSoundType(SoundType.STONE);
            setHarvestLevel("pickaxe", 1);
            setHardness(6F);
            setResistance(15F);
            setLightOpacity(255);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) { return 1; }
        @Override public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) { return 4; }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.BLACK; }
    }
    public static class BlockCompressedCobblestone extends Block {
        public BlockCompressedCobblestone() {
            super(Material.ROCK);
            setRegistryName(MOD_ID, "compressed_cobblestone");
            setUnlocalizedName("compressed_cobblestone");
            setSoundType(SoundType.STONE);
            setHarvestLevel("pickaxe", 2);
            setHardness(6F);
            setResistance(15F);
            setLightOpacity(255);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.GRAY; }
    }
    public static class BlockCompressedCopperBlock extends Block {
        public BlockCompressedCopperBlock() {
            super(Material.IRON);
            setRegistryName(MOD_ID, "compressed_copper_block");
            setUnlocalizedName("compressed_copper_block");
            setSoundType(SoundType.METAL);
            setHarvestLevel("pickaxe", 2);
            setHardness(9F);
            setResistance(35F);
            setLightOpacity(255);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.ORANGE_STAINED_HARDENED_CLAY; }
    }
    public static class BlockCompressedDiamondBlock extends Block {
        public BlockCompressedDiamondBlock() {
            super(Material.ROCK);
            setRegistryName(MOD_ID, "compressed_diamond_block");
            setUnlocalizedName("compressed_diamond_block");
            setSoundType(SoundType.STONE);
            setHarvestLevel("pickaxe", 3);
            setHardness(13F);
            setResistance(50F);
            setLightOpacity(255);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.DIAMOND; }
    }
    public static class BlockCompressedEmeraldBlock extends Block {
        public BlockCompressedEmeraldBlock() {
            super(Material.ROCK);
            setRegistryName(MOD_ID, "compressed_emerald_block");
            setUnlocalizedName("compressed_emerald_block");
            setSoundType(SoundType.STONE);
            setHarvestLevel("pickaxe", 21);
            setHardness(11F);
            setResistance(30F);
            setLightOpacity(255);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.LIME; }
    }
    public static class BlockCompressedEndstone extends Block {
        public BlockCompressedEndstone() {
            super(Material.ROCK);
            setRegistryName(MOD_ID, "compressed_endstone");
            setUnlocalizedName("compressed_endstone");
            setSoundType(SoundType.STONE);
            setHarvestLevel("pickaxe", 2);
            setHardness(7F);
            setResistance(20F);
            setLightOpacity(255);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.GOLD; }
    }
    public static class BlockCompressedGoldBlock extends Block {
        public BlockCompressedGoldBlock() {
            super(Material.IRON);
            setRegistryName(MOD_ID, "compressed_gold_block");
            setUnlocalizedName("compressed_gold_block");
            setSoundType(SoundType.METAL);
            setHarvestLevel("pickaxe", 1);
            setHardness(7F);
            setResistance(30F);
            setLightOpacity(255);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.YELLOW; }
    }
    public static class BlockCompressedIronBlock extends Block {
        public BlockCompressedIronBlock() {
            super(Material.IRON);
            setRegistryName(MOD_ID, "compressed_iron_block");
            setUnlocalizedName("compressed_iron_block");
            setSoundType(SoundType.METAL);
            setHarvestLevel("pickaxe", 2);
            setHardness(8F);
            setResistance(40F);
            setLightOpacity(255);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.IRON; }
    }
    public static class BlockCompressedLapisBlock extends Block {
        public BlockCompressedLapisBlock() {
            super(Material.ROCK);
            setRegistryName(MOD_ID, "compressed_lapis_block");
            setUnlocalizedName("compressed_lapis_block");
            setSoundType(SoundType.STONE);
            setHarvestLevel("pickaxe", 2);
            setHardness(6F);
            setResistance(35F);
            setLightOpacity(255);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.BLUE; }
    }
    public static class BlockCompressedLeadBlock extends Block {
        public BlockCompressedLeadBlock() {
            super(Material.IRON);
            setRegistryName(MOD_ID, "compressed_lead_block");
            setUnlocalizedName("compressed_lead_block");
            setSoundType(SoundType.METAL);
            setHarvestLevel("pickaxe", 2);
            setHardness(10F);
            setResistance(70F);
            setLightOpacity(255);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.GRAY_STAINED_HARDENED_CLAY; }
    }
    public static class BlockCompressedQuartz extends Block {
        public BlockCompressedQuartz() {
            super(Material.ROCK);
            setRegistryName(MOD_ID, "compressed_quartz");
            setUnlocalizedName("compressed_quartz");
            setSoundType(SoundType.STONE);
            setHarvestLevel("pickaxe", 1);
            setHardness(3F);
            setResistance(8F);
            setLightOpacity(255);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.QUARTZ; }
    }
    public static class BlockCompressedRedstoneBlock extends Block {
        public BlockCompressedRedstoneBlock() {
            super(Material.ROCK);
            setRegistryName(MOD_ID, "compressed_redstone_block");
            setUnlocalizedName("compressed_redstone_block");
            setSoundType(SoundType.STONE);
            setHarvestLevel("pickaxe", 2);
            setHardness(6F);
            setResistance(35F);
            setLightOpacity(255);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.RED; }
    }
    public static class BlockCompressedSand extends BlockFalling {
        public BlockCompressedSand() {
            super(Material.ROCK);
            setRegistryName(MOD_ID, "compressed_sand");
            setUnlocalizedName("compressed_sand");
            setSoundType(SoundType.STONE);
            setHarvestLevel("shovel", 1);
            setHardness(2.5F);
            setResistance(6F);
            setLightOpacity(255);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.SAND; }
    }
    public static class BlockCompressedSteelBlock extends Block {
        public BlockCompressedSteelBlock() {
            super(Material.IRON);
            setRegistryName(MOD_ID, "compressed_steel_block");
            setUnlocalizedName("compressed_steel_block");
            setSoundType(SoundType.METAL);
            setHarvestLevel("pickaxe", 3);
            setHardness(13F);
            setResistance(65F);
            setLightOpacity(255);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.GRAY; }
    }
    public static class BlockCompressedTinBlock extends Block {
        public BlockCompressedTinBlock() {
            super(Material.IRON);
            setRegistryName(MOD_ID, "compressed_tin_block");
            setUnlocalizedName("compressed_tin_block");
            setSoundType(SoundType.METAL);
            setHarvestLevel("pickaxe", 2);
            setHardness(7F);
            setResistance(30F);
            setLightOpacity(255);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.GRAY; }
    }
    public static class BlockDecoBlock1 extends Block {
        public BlockDecoBlock1() {
            super(Material.IRON);
            setRegistryName(MOD_ID, "deco_block_1");
            setUnlocalizedName("deco_block_1");
            setSoundType(SoundType.METAL);
            setHarvestLevel("pickaxe", 1);
            setHardness(1F);
            setResistance(10F);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
    }
    public static class BlockDecoBlockGrid extends Block {
        public BlockDecoBlockGrid() {
            super(Material.GLASS);
            setRegistryName(MOD_ID, "deco_block_grid");
            setUnlocalizedName("deco_block_grid");
            setSoundType(SoundType.METAL);
            setHardness(2F);
            setResistance(3F);
            setLightOpacity(0);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @SideOnly(Side.CLIENT)
        @Override public BlockRenderLayer getBlockLayer() { return BlockRenderLayer.TRANSLUCENT; }
        @Override public boolean isOpaqueCube(IBlockState state) { return false; }
    }
    public static class BlockDeepDiamondOre extends Block {
        public BlockDeepDiamondOre() {
            super(Material.ROCK);
            setRegistryName(MOD_ID, "deep_diamond_ore");
            setUnlocalizedName("deep_diamond_ore");
            setSoundType(SoundType.STONE);
            setHarvestLevel("pickaxe", 2);
            setHardness(4.5F);
            setResistance(10F);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.GRAY; }
        @Override public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) { drops.add(new ItemStack(Blocks.DIAMOND_ORE, 2)); }
    }
    public static class BlockDeepEmeraldOre extends Block {
        public BlockDeepEmeraldOre() {
            super(Material.ROCK);
            setRegistryName(MOD_ID, "deep_emerald_ore");
            setUnlocalizedName("deep_emerald_ore");
            setSoundType(SoundType.STONE);
            setHarvestLevel("pickaxe", 2);
            setHardness(4.5F);
            setResistance(10F);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.GRAY; }
        @Override public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) { drops.add(new ItemStack(Blocks.EMERALD_ORE, 2)); }
    }
    public static class BlockDeepGoldOre extends Block {
        public BlockDeepGoldOre() {
            super(Material.ROCK);
            setRegistryName(MOD_ID, "deep_gold_ore");
            setUnlocalizedName("deep_gold_ore");
            setSoundType(SoundType.STONE);
            setHarvestLevel("pickaxe", 2);
            setHardness(4.5F);
            setResistance(10F);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) { drops.add(new ItemStack(Blocks.GOLD_ORE, 2)); }
    }
    public static class BlockDeepIronOre extends Block {
        public BlockDeepIronOre() {
            super(Material.ROCK);
            setRegistryName(MOD_ID, "deep_iron_ore");
            setUnlocalizedName("deep_iron_ore");
            setSoundType(SoundType.STONE);
            setHarvestLevel("pickaxe", 1);
            setHardness(4.5F);
            setResistance(10F);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.GRAY; }
        @Override public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) { drops.add(new ItemStack(Blocks.IRON_ORE, 2)); }
    }
    public static class BlockDeepLapisOre extends Block {
        public BlockDeepLapisOre() {
            super(Material.ROCK);
            setRegistryName(MOD_ID, "deep_lapis_ore");
            setUnlocalizedName("deep_lapis_ore");
            setSoundType(SoundType.STONE);
            setHarvestLevel("pickaxe", 2);
            setHardness(4.5F);
            setResistance(10F);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.GRAY; }
        @Override public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) { drops.add(new ItemStack(Blocks.LAPIS_ORE, 2)); }
    }
    public static class BlockDeepRedstoneOre extends Block {
        public BlockDeepRedstoneOre() {
            super(Material.ROCK);
            setRegistryName(MOD_ID, "deep_redstone_ore");
            setUnlocalizedName("deep_redstone_ore");
            setSoundType(SoundType.STONE);
            setHarvestLevel("pickaxe", 2);
            setHardness(4.5F);
            setResistance(10F);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.GRAY; }
        @Override public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) { drops.add(new ItemStack(Blocks.REDSTONE_ORE, 2)); }
    }
    public static class BlockDeepslateBricks extends Block {
        public BlockDeepslateBricks() {
            super(Material.ROCK);
            setRegistryName(MOD_ID, "deepslate_bricks");
            setUnlocalizedName("deepslate_bricks");
            setSoundType(SoundType.STONE);
            setHarvestLevel("pickaxe", 0);
            setHardness(4.5F);
            setResistance(16F);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
    }
    public static class BlockDeepslateBricksStairs extends BlockStairs {
        public BlockDeepslateBricksStairs() {
            super(new Block(Material.ROCK).getDefaultState());
            setRegistryName(MOD_ID, "deepslate_bricks_stairs");
            setUnlocalizedName("deepslate_bricks_stairs");
            setSoundType(SoundType.STONE);
            setHarvestLevel("pickaxe", 0);
            setHardness(4.5F);
            setResistance(16F);
            setLightOpacity(255);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
    }
    public static class BlockDeepslateTiles extends Block {
        public BlockDeepslateTiles() {
            super(Material.ROCK);
            setRegistryName(MOD_ID, "deepslate_tiles");
            setUnlocalizedName("deepslate_tiles");
            setSoundType(SoundType.STONE);
            setHarvestLevel("pickaxe", 0);
            setHardness(4.5F);
            setResistance(12F);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
    }
    public static class BlockDeepslateTilesStairs extends BlockStairs {
        public BlockDeepslateTilesStairs() {
            super(new Block(Material.ROCK).getDefaultState());
            setRegistryName(MOD_ID, "deepslate_tiles_stairs");
            setUnlocalizedName("deepslate_tiles_stairs");
            setSoundType(SoundType.STONE);
            setHarvestLevel("pickaxe", 0);
            setHardness(4.5F);
            setResistance(12F);
            setLightOpacity(255);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
    }
    public static class BlockDirtBricks extends Block {
        public BlockDirtBricks() {
            super(Material.GROUND);
            setRegistryName(MOD_ID, "dirt_bricks");
            setUnlocalizedName("dirt_bricks");
            setSoundType(SoundType.GROUND);
            setHarvestLevel("shovel", 0);
            setHardness(0.5F);
            setResistance(1F);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plant) {
            EnumPlantType plantType = plant.getPlantType(world, pos.offset(direction));
            return plantType == EnumPlantType.Plains;
        }
    }
    public static class BlockDontWalk extends Block {
        public BlockDontWalk() {
            super(Material.IRON);
            setRegistryName(MOD_ID, "dont_walk_tape");
            setUnlocalizedName("dont_walk_tape");
            setSoundType(SoundType.METAL);
            setHarvestLevel("pickaxe", 1);
            setHardness(3F);
            setResistance(25F);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.BLUE; }
    }
    public static class BlockDoubleCompressedQuartz extends Block {
        public BlockDoubleCompressedQuartz() {
            super(Material.ROCK);
            setRegistryName(MOD_ID, "double_compressed_quartz");
            setUnlocalizedName("double_compressed_quartz");
            setSoundType(SoundType.STONE);
            setHarvestLevel("pickaxe", 2);
            setHardness(7F);
            setResistance(20F);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.QUARTZ; }
    }
    public static class BlockMetalFrameBW extends Block {
        public BlockMetalFrameBW() {
            super(Material.IRON);
            setRegistryName(MOD_ID, "metal_frame");
            setUnlocalizedName("metal_frame");
            setSoundType(SoundType.METAL);
            setHarvestLevel("pickaxe", 1);
            setHardness(2F);
            setResistance(10F);
            setLightOpacity(0);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @SideOnly(Side.CLIENT)
        @Override public BlockRenderLayer getBlockLayer() { return BlockRenderLayer.TRANSLUCENT; }
        @Override public boolean isOpaqueCube(IBlockState state) { return false; }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.AIR; }
    }
    public static class BlockMineralSteel extends Block {
        public BlockMineralSteel() {
            super(Material.IRON);
            setRegistryName(MOD_ID, "mineral_steel");
            setUnlocalizedName("mineral_steel");
            setSoundType(SoundType.METAL);
            setHarvestLevel("pickaxe", 3);
            setHardness(10F);
            setResistance(30F);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.GRAY; }
    }
    public static class BlockNetherstarBlock extends Block {
        public BlockNetherstarBlock() {
            super(Material.IRON);
            setRegistryName(MOD_ID, "netherstar_block");
            setUnlocalizedName("netherstar_block");
            setSoundType(SoundType.METAL);
            setHarvestLevel("pickaxe", 1);
            setHardness(50F);
            setResistance(120F);
            setLightLevel(0.466666666667F);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public EnumPushReaction getMobilityFlag(IBlockState state) { return EnumPushReaction.IGNORE; }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.SNOW; }
    }
    public static class BlockNullBlockx extends Block {
        public BlockNullBlockx() {
            super(Material.IRON);
            setRegistryName(MOD_ID, "null_block");
            setUnlocalizedName("null_block");
            setSoundType(SoundType.CLOTH);
            setHardness(0F);
            setResistance(0F);
            setLightOpacity(0);
            setCreativeTab(null);
        }
        @SideOnly(Side.CLIENT)
        @Override public BlockRenderLayer getBlockLayer() { return BlockRenderLayer.TRANSLUCENT; }
        @Override public boolean isOpaqueCube(IBlockState state) { return false; }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.AIR; }
    }
    public static class BlockPotatoBlock extends BlockFalling {
        public BlockPotatoBlock() {
            super(Material.SAND);
            setRegistryName(MOD_ID, "potato_block");
            setUnlocalizedName("potato_block");
            setSoundType(SoundType.WOOD);
            setHarvestLevel("shovel", 0);
            setHardness(2F);
            setResistance(8F);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.WOOD; }
    }
    public static class BlockRadiationHazard extends Block {
        public BlockRadiationHazard() {
            super(Material.IRON);
            setRegistryName(MOD_ID, "radiation_hazard_tape");
            setUnlocalizedName("radiation_hazard_tape");
            setSoundType(SoundType.METAL);
            setHarvestLevel("pickaxe", 1);
            setHardness(3F);
            setResistance(30F);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.PINK; }
    }
    public static class BlockRubyBlock extends Block {
        public BlockRubyBlock() {
            super(Material.ROCK);
            setRegistryName(MOD_ID, "ruby_block");
            setUnlocalizedName("ruby_block");
            setSoundType(SoundType.STONE);
            setHarvestLevel("pickaxe", 2);
            setHardness(5F);
            setResistance(30F);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.RED; }
    }
    public static class BlockSafetyZone extends Block {
        public BlockSafetyZone() {
            super(Material.IRON);
            setRegistryName(MOD_ID, "safety_zone_tape");
            setUnlocalizedName("safety_zone_tape");
            setSoundType(SoundType.METAL);
            setHarvestLevel("pickaxe", 1);
            setHardness(3F);
            setResistance(25F);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.LIME; }
    }
    public static class BlockSugarBlock extends BlockFalling {
        public BlockSugarBlock() {
            super(Material.GROUND);
            setRegistryName(MOD_ID, "sugar_block");
            setUnlocalizedName("sugar_block");
            setSoundType(SoundType.SAND);
            setHarvestLevel("shovel", 0);
            setHardness(3F);
            setResistance(4F);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.SNOW; }
    }
    public static class BlockTechnicalWorks extends Block {
        public BlockTechnicalWorks() {
            super(Material.IRON);
            setRegistryName(MOD_ID, "technical_works_tape");
            setUnlocalizedName("technical_works_tape");
            setSoundType(SoundType.METAL);
            setHarvestLevel("pickaxe", 1);
            setHardness(3F);
            setResistance(25F);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.ORANGE_STAINED_HARDENED_CLAY; }
    }
    public static class BlockTripleCompressedQuartz extends Block {
        public BlockTripleCompressedQuartz() {
            super(Material.ROCK);
            setRegistryName(MOD_ID, "triple_compressed_quartz");
            setUnlocalizedName("triple_compressed_quartz");
            setSoundType(SoundType.STONE);
            setHarvestLevel("pickaxe", 3);
            setHardness(19F);
            setResistance(45F);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
        }
        @Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.QUARTZ; }
    }








	public static class BlockDeepslate extends Block {
		public BlockDeepslate() {
			super(Material.ROCK);
            setRegistryName(MOD_ID, "deepslate");
			setUnlocalizedName("deepslate");
			setSoundType(SoundType.STONE);
			setHarvestLevel("pickaxe", 0);
			setHardness(3.5F);
			setResistance(15F);
			setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
		}
		@Override public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
			drops.add(new ItemStack(ATBlocks.COBBLED_DEEPSLATE, 1));
		}
	}
	public static class BlockFexplosive extends Block {
	    public static final PropertyDirection FACING = BlockHorizontal.FACING;
	    public BlockFexplosive() {
	        super(Material.CLOTH);
            setRegistryName(MOD_ID, "fexplosive");
	        setUnlocalizedName("fexplosive");
	        setSoundType(SoundType.PLANT);
	        setHardness(1F);
	        setResistance(0F);
	        setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
	        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	    }
	    @Override protected BlockStateContainer createBlockState() { return new BlockStateContainer(this, FACING); }
	    @Override public IBlockState withRotation(IBlockState state, Rotation rot) { return state.withProperty(FACING, rot.rotate(state.getValue(FACING))); }
	    @Override public IBlockState withMirror(IBlockState state, Mirror mirrorIn) { return state.withRotation(mirrorIn.toRotation(state.getValue(FACING))); }
	    @Override public IBlockState getStateFromMeta(int meta) {
	        // Use horizontal index (0..3) for BlockHorizontal
	        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
	    }
	    @Override
	    public int getMetaFromState(IBlockState state) {
	        return state.getValue(FACING).getHorizontalIndex();
	    }
	    @Override
	    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
	        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	    }
	    @Override
	    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos fromPos) {
	        super.neighborChanged(state, world, pos, neighborBlock, fromPos);
	        if (!world.isRemote && world.isBlockIndirectlyGettingPowered(pos) > 0) {
	            initExp(world, pos);
	        }
	    }
	    @Override
	    public void onBlockDestroyedByExplosion(World world, BlockPos pos, Explosion e) {
	        super.onBlockDestroyedByExplosion(world, pos, e);
	        if (!world.isRemote) initExp(world, pos);
	    }
	    @Override
	    public void onBlockClicked(World world, BlockPos pos, EntityPlayer entity) {
	        super.onBlockClicked(world, pos, entity);
	        if (!world.isRemote) initExp(world, pos);
	    }
	    @Override
	    public boolean onBlockActivated(World world,BlockPos pos,IBlockState state, EntityPlayer entity,EnumHand hand,EnumFacing direction, float hitX, float hitY, float hitZ) {
	        super.onBlockActivated(world, pos, state, entity, hand, direction, hitX, hitY, hitZ);
	        if (!world.isRemote) initExp(world, pos);
	        return true;
	    }
	    private static void initExp(World world, BlockPos pos) {
	        // server-side only already checked by callers; defensive check:
	        if (world.isRemote) return;
	        double x = pos.getX() + 0.5;
	        double y = pos.getY() + 0.5;
	        double z = pos.getZ() + 0.5;
	        // createExplosion(Entity exploder, double x, double y, double z, float strength, boolean createsFire)
	        world.setBlockToAir(pos);
	        world.createExplosion(null, x, y, z, 2.0F, true);
	        // Optionally: world.setBlockToAir(pos); if you want to remove block explicitly
	    }
	}

    




}







