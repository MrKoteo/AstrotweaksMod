package astrotweaks.block;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.world.World;
import net.minecraft.world.Explosion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;

import java.util.ArrayList;
import java.util.List;

import astrotweaks.ModVariables;

@Mod.EventBusSubscriber(modid = "astrotweaks")
public class MinedBlocks {
	public static final String MOD_ID = "astrotweaks";

	// Format: "registryName" or "registryName:*" (wildcard expands to 16 metas)
	private static final String[] BLOCK_DATA = {
	    "minecraft:stone",
	    "minecraft:dirt",
	    "minecraft:cobblestone",
	    "minecraft:sandstone",
	    "minecraft:mossy_cobblestone",
	    "minecraft:brick_block",
	    "minecraft:bookshelf",
	    "minecraft:snow",
	    "minecraft:clay",
	    "minecraft:pumpkin",
	    "minecraft:netherrack",
	    "minecraft:end_stone",
	    "minecraft:glowstone",
	    "minecraft:lit_pumpkin",
	    "minecraft:stonebrick",
	    "minecraft:melon_block",
	    "minecraft:hay_block",
	    "minecraft:red_sandstone",
	    "minecraft:purpur_block",
	    "minecraft:end_bricks",
	    "minecraft:bone_block",
	    "minecraft:nether_brick",
	    "minecraft:hardened_clay",
	    "minecraft:prismarine",
	    "minecraft:sea_lantern",
	    "minecraft:packed_ice",
	    "minecraft:sponge",
	    "minecraft:sand",
	    "minecraft:mycelium",
	    "astrotweaks:dirt_bricks",
	    "astrotweaks:ruby_block",
	    "astrotweaks:brass_block",
	    "astrotweaks:ruby_ore",
	    "astrotweaks:quartz_ore_stone",
	    "astrotweaks:quartz_ore_granite",
	    "minecraft:coal_ore",
	    "minecraft:iron_ore",
	    "minecraft:gold_ore",
	    "minecraft:diamond_ore",
	    "minecraft:emerald_ore",
	    "minecraft:lapis_ore",
	    "minecraft:redstone_ore",
	    "minecraft:quartz_ore",
	    "minecraft:coal_block",
	    "minecraft:iron_block",
	    "minecraft:gold_block",
	    "minecraft:diamond_block",
	    "minecraft:emerald_block",
	    "minecraft:lapis_block",
	    "minecraft:redstone_block",
	    "minecraft:white_glazed_terracotta",
	    "minecraft:orange_glazed_terracotta",
	    "minecraft:magenta_glazed_terracotta",
	    "minecraft:light_blue_glazed_terracotta",
	    "minecraft:yellow_glazed_terracotta",
	    "minecraft:lime_glazed_terracotta",
	    "minecraft:pink_glazed_terracotta",
	    "minecraft:gray_glazed_terracotta",
	    "minecraft:silver_glazed_terracotta",
	    "minecraft:cyan_glazed_terracotta",
	    "minecraft:purple_glazed_terracotta",
	    "minecraft:blue_glazed_terracotta",
	    "minecraft:brown_glazed_terracotta",
	    "minecraft:green_glazed_terracotta",
	    "minecraft:red_glazed_terracotta",
	    "minecraft:black_glazed_terracotta",
	    "astrotweaks:minerals_ore",
	    "astrotweaks:deep_minerals",
	    "astrotweaks:deep_diamond_ore",
	    "astrotweaks:deep_gold_ore",
	    "astrotweaks:deep_emerald_ore",
	    "astrotweaks:deep_iron_ore",
	    "astrotweaks:deep_lapis_ore",
	    "astrotweaks:deep_redstone_ore",
	    "astrotweaks:deepslate",
	    "astrotweaks:deepslate_bricks",
	    "astrotweaks:cobbled_deepslate",
	    "astrotweaks:deepslate_tiles",
	    "astrotweaks:bread_block",
	    "astrotweaks:potato_block",
	    "astrotweaks:carrot_block",
	    "astrotweaks:apple_block",
	    "astrotweaks:beetroot_block"
	};

    private static Entry[] ENTRIES_BY_META;

    static class Entry {
        final int globalIndex;
        final String regName;      // registryName, ex: "minecraft:stone"
        ModelResourceLocation modelLoc;

        Entry(int globalIndex, String regName) {
            this.globalIndex = globalIndex;
            this.regName = regName;
        }
    }

	static {
	    // one-pass build: expand wildcards (":*") into 16 sequential entries
	    List<Entry> tmp = new ArrayList<>();
	    int currentIndex = 0;
	    for (String line : BLOCK_DATA) {
	        if (line == null || line.trim().isEmpty()) continue;
	        String reg = line.trim();
	        if (reg.endsWith(":*")) {
	            String base = reg.substring(0, reg.length() - 2); // e.g. "minecraft:wool"
	            for (int m = 0; m < 16; m++) {
	                tmp.add(new Entry(currentIndex, base + ":" + m));
	                currentIndex++;
	            }
	        } else {
	            tmp.add(new Entry(currentIndex, reg));
	            currentIndex++;
	        }
	    }
	    // transfer to array
	    ENTRIES_BY_META = new Entry[tmp.size()];
	    for (int i = 0; i < tmp.size(); i++) ENTRIES_BY_META[i] = tmp.get(i);
	}

    private static final int GROUP_SIZE = 16;
    private static final List<Block> groupBlocks = new ArrayList<>();
    private static final List<Item> groupItems = new ArrayList<>();
    private static boolean built = false;

    private static void build() {
    	if (built) return;
    	built = true;
    	if (!ModVariables.doRegisterMinedBlocks) return;
        int totalVariants = ENTRIES_BY_META.length;
        int groups = (totalVariants + GROUP_SIZE - 1) / GROUP_SIZE;

        for (int g = 0; g < groups; g++) {
            final int groupIndex = g;
            final int startGlobal = g * GROUP_SIZE;
            final int localCount = Math.min(GROUP_SIZE, totalVariants - startGlobal);
            // registry name: mined_block, mined_block_1, mined_block_2, ...
            String regName = groupIndex == 0 ? "mined_block" : "mined_block_" + groupIndex;

            Block block = createGroupBlock(startGlobal, localCount, regName);
            groupBlocks.add(block);

            ItemBlock item = createGroupItem(block, groupIndex, startGlobal, localCount);
            item.setRegistryName(block.getRegistryName());
            groupItems.add(item);
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        build();
        for (Block block : groupBlocks) event.getRegistry().register(block);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        build();
        for (Item item : groupItems) event.getRegistry().register(item);
    }

    // Create Block for group
	private static Block createGroupBlock(final int startGlobal, final int localCount, String registryName) {
	    final PropertyInteger prop = PropertyInteger.create("meta", 0, Math.max(1, localCount - 1));
	    return new Block(Material.ROCK) {
	        {
	            setUnlocalizedName(registryName);
	            setRegistryName("astrotweaks", registryName);
	            setHardness(0.25F);
				setResistance(1F);
	            setCreativeTab(null);
	            this.setDefaultState(this.blockState.getBaseState().withProperty(prop, 0));
	        }
	        @Override
	        public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
	            for (int m = 0; m < localCount; m++) {
	                int globalIndex = startGlobal + m;
	                if (globalIndex >= 0 && globalIndex < ENTRIES_BY_META.length && ENTRIES_BY_META[globalIndex] != null) {
	                    items.add(new ItemStack(this, 1, m));
	                }
	            }
	        }
	        @Override
	        public float getBlockHardness(IBlockState state, World world, BlockPos pos) {
	            return 1.5f;
	        }
			@Override
			public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
			    if (player != null && !player.capabilities.isCreativeMode && !world.isRemote) {
			        world.createExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 2.5f, true);
			    }
			    super.onBlockHarvested(world, pos, state, player);
			}
	        @Override
	        public void onBlockDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
	            if (!world.isRemote) world.createExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 2.5f, true);
	            super.onBlockDestroyedByExplosion(world, pos, explosion);
	        }
	        @Override
	        protected BlockStateContainer createBlockState() {
	            return new BlockStateContainer(this, prop);
	        }
	        @Override
	        public IBlockState getStateFromMeta(int meta) {
	            int m = (meta >= 0 && meta < localCount) ? meta : 0;
	            int maxAllowed = prop.getAllowedValues().stream().mapToInt(Integer::intValue).max().orElse(1);
	            if (m > maxAllowed) m = maxAllowed;
	            return this.getDefaultState().withProperty(prop, m);
	        }
	        @Override
	        public int getMetaFromState(IBlockState state) {
	            int value = state.getValue(prop);
	            return (value >= 0 && value < localCount) ? value : 0;
	        }
	    };
	}
    // Create ItemBlock for group; getUnlocalizedName uses global index
    private static ItemBlock createGroupItem(final Block block, final int groupIndex, final int startGlobal, final int localCount) {
        return new ItemBlock(block) {
            {
                setHasSubtypes(true);
                setMaxDamage(0);
            }

            @Override
            public int getMetadata(int damage) {
                return damage;
            }
            @Override
            public String getUnlocalizedName(ItemStack stack) {
                int localMeta = stack.getMetadata();
                int globalIndex = startGlobal + localMeta;
                Entry e = (globalIndex >= 0 && globalIndex < ENTRIES_BY_META.length) ? ENTRIES_BY_META[globalIndex] : null;
                if (e == null) return "tile.mined_block.unknown.name";
                return "tile.mined_block." + e.globalIndex;
            }
        };
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, value = Side.CLIENT)
    public static class ClientHandler {
        @SideOnly(Side.CLIENT)
        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {
            build();
            for (int g = 0; g < groupItems.size(); g++) {
                Item item = groupItems.get(g);
                //Block block = groupBlocks.get(g);
                int startGlobal = g * GROUP_SIZE;
                int localCount = Math.min(GROUP_SIZE, ENTRIES_BY_META.length - startGlobal);
                for (int local = 0; local < localCount; local++) {
                    int globalIndex = startGlobal + local;
                    Entry entry = (globalIndex >= 0 && globalIndex < ENTRIES_BY_META.length) ? ENTRIES_BY_META[globalIndex] : null;
                    if (entry == null) continue;
                    if (entry.modelLoc == null) {
                        String[] parts = entry.regName.split(":");
                        String domain = parts.length > 1 ? parts[0] : "minecraft";
                        String path = parts.length > 1 ? parts[1] : entry.regName;
                        entry.modelLoc = new ModelResourceLocation(new ResourceLocation(domain, path), "inventory");
                    }
                    ModelLoader.setCustomModelResourceLocation(item, local, entry.modelLoc);
                }
            }
        }
    }
}