package astrotweaks.block;

import astrotweaks.creativetab.ATCreativeTabs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = "astrotweaks")
public class ATBlocks {
    public static final String MOD_ID = "astrotweaks";



    public static final Block BEETROOT_BLOCK = new BlockBeetroot();
    public static final Block HAZARD_ZONE_TAPE = new BlockHazardZoneTape();










// -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

    private ATBlocks() {}

    private static final Block[] BLOCKS_TO_REGISTER = {
        BEETROOT_BLOCK,
        HAZARD_ZONE_TAPE
    };

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(BLOCKS_TO_REGISTER);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        for (Block block : BLOCKS_TO_REGISTER) {
            event.getRegistry().register( new ItemBlock(block).setRegistryName(block.getRegistryName()) );
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, value = net.minecraftforge.fml.relauncher.Side.CLIENT)
    public static class ClientHandler {
        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {
            for (Block block : BLOCKS_TO_REGISTER) {
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(),"inventory")
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
        // @Override с одним параметром можно ужать в одну строку. Строк в @Overridе несколько, пишем как есть.
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
}
