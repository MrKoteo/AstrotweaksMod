package astrotweaks.block;

import astrotweaks.ElementsAstrotweaksMod;
import astrotweaks.creativetab.ATCreativeTabs;
import astrotweaks.util.DropHandler;
import astrotweaks.util.DropHandler.DropEntry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFire;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
//import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@ElementsAstrotweaksMod.ModElement.Tag
public class BlockGiantGrass extends ElementsAstrotweaksMod.ModElement {

    @GameRegistry.ObjectHolder("astrotweaks:giant_grass")
    public static final Block block = null;
    public BlockGiantGrass(ElementsAstrotweaksMod instance) {
        super(instance, 1121);
    }
    @Override
    public void initElements() {
        elements.blocks.add(() -> new BlockCustom().setRegistryName("giant_grass"));
        elements.items.add(() -> new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }
    @SideOnly(Side.CLIENT)
    @Override public void registerModels(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation("astrotweaks:giant_grass", "inventory"));
    }

    public static class BlockCustom extends BlockBush {
        public static final PropertyEnum<Part> PART = PropertyEnum.create("part", Part.class);

        public BlockCustom() {
            super(Material.VINE);
            setUnlocalizedName("giant_grass");
            setSoundType(SoundType.PLANT);
            setHardness(0.0F);
            setResistance(0.0F);
            setLightOpacity(0);
            setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
            setTickRandomly(true);

            setDefaultState(blockState.getBaseState().withProperty(PART, Part.LOWER)
            );

            // Свойства горения примерно как у ванильной травы/растительности
            BlockFire fire = (BlockFire) net.minecraft.init.Blocks.FIRE;
            fire.setFireInfo(this, 60, 100);
        }

        // array of possible drops
        private static final DropEntry[] DE_TABLE = new DropEntry[] {
            new DropEntry("minecraft:wheat_seeds",    1, 8.0),
			new DropEntry("minecraft:tallgrass",    1, 2.0, 1),
            new DropEntry("none",    			1, 90.0)
        };
		private static final DropHandler DE_DROP_TABLE = new DropHandler(DE_TABLE);
		@Override public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		    World w = (world instanceof World) ? (World) world : null;
		    int rolls = 2;
		    DE_DROP_TABLE.generateDrops(drops, w, rolls);
		}

        @Override public boolean isOpaqueCube(IBlockState state) { return false; }
        @Override public boolean isFullCube(IBlockState state) { return false; }
        @Override public boolean isPassable(IBlockAccess world, BlockPos pos) { return true; }
        @Override public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) { return NULL_AABB; }
		@Override public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) { return FULL_BLOCK_AABB; }
        @SideOnly(Side.CLIENT)
		@Override public net.minecraft.util.BlockRenderLayer getBlockLayer() {
            return net.minecraft.util.BlockRenderLayer.CUTOUT;
        }
        @Override public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos) { return MapColor.GRASS; }
        @Override
        public boolean canPlaceBlockAt(World world, BlockPos pos) {
            BlockPos middle = pos.up();
            BlockPos upper = pos.up(2);
            return super.canPlaceBlockAt(world, pos) && world.getBlockState(middle).getMaterial().isReplaceable() && world.getBlockState(upper).getMaterial().isReplaceable();
        }
        @Override
        public IBlockState getStateFromMeta(int meta) {
            Part part;

            switch (meta) {
                case 1:
                    part = Part.MIDDLE;
                    break;

                case 2:
                    part = Part.UPPER;
                    break;

                case 0:
                default:
                    part = Part.LOWER;
                    break;
            }
            return getDefaultState().withProperty(PART, part);
        }

        @Override
        public int getMetaFromState(IBlockState state) {
            switch (state.getValue(PART)) {
                case MIDDLE:
                    return 1;
                case UPPER:
                    return 2;
                case LOWER:
                default:
                    return 0;
            }
        }

        @Override
        protected net.minecraft.block.state.BlockStateContainer createBlockState() {
            return new net.minecraft.block.state.BlockStateContainer(this, PART);
        }

        /**
         * После установки нижнего блока создаём средний и верхний.
         */
        @Override
        public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, net.minecraft.entity.EntityLivingBase placer, net.minecraft.item.ItemStack stack) {
			if (world.isRemote) return;

			IBlockState middleState = getDefaultState().withProperty(PART, Part.MIDDLE);
			IBlockState upperState = getDefaultState().withProperty(PART, Part.UPPER);

			world.setBlockState(pos.up(), middleState, 2);
			world.setBlockState(pos.up(2), upperState, 2);
        }

        /**
         * Удаляем всё, если ломается любой сегмент
         */
        @Override
        public void breakBlock(World world, BlockPos pos, IBlockState state) {
            Part part = state.getValue(PART);
            BlockPos base;

            switch (part) {
                case MIDDLE:
                    base = pos.down();
                    break;
                case UPPER:
                    base = pos.down(2);
                    break;
                case LOWER:
                default:
                    base = pos;
                    break;
            }
            for (int i = 0; i < 3; i++) {
                BlockPos partPos = base.up(i);

                if (!partPos.equals(pos) && world.getBlockState(partPos).getBlock() == this) {
                    world.setBlockToAir(partPos);
                }
            }
            super.breakBlock(world, pos, state);
        }

		@Override
		public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
			Part part = state.getValue(PART);
			switch (part) {
				case LOWER:
					// Для нижней части - проверяем опору на землю (как у ванильного растения)
					return canSustainBush(world.getBlockState(pos.down()));
				case MIDDLE:
					// Средняя часть должна иметь нижнюю под собой
					return world.getBlockState(pos.down()).getBlock() == this &&
						world.getBlockState(pos.down()).getValue(PART) == Part.LOWER;
				case UPPER:
					// Верхняя часть должна иметь среднюю под собой
					return world.getBlockState(pos.down()).getBlock() == this &&
						world.getBlockState(pos.down()).getValue(PART) == Part.MIDDLE;
				default:
					return false;
			}
		}

		@Override
		public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
			// Вызываем нашу проверку валидности
			if (!canBlockStay(world, pos, state)) {
				// Если блок невалиден, удаляем его
				world.setBlockToAir(pos);
				// Важно: super.neighborChanged не вызываем, чтобы избежать лишней логики
			}
			// Можно также уведомить соседей, но не обязательно
		}


        @Override
        public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
            return BlockFaceShape.UNDEFINED;
        }
		@Override
		public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
			return EnumPlantType.Plains;
		}
        @Override
        public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
            return world.getBlockState(pos);
        }
        //@Override
        //public Item getItemDropped(IBlockState state, java.util.Random random, int fortune) {
        //    // Предмет выпадает только при разрушении нижней части
        //    return state.getValue(PART) == Part.LOWER ? Item.getItemFromBlock(this) : null;
        //}
        public enum Part implements IStringSerializable {
            LOWER("lower"),
            MIDDLE("middle"),
            UPPER("upper");

            private final String name;

            Part(String name) {
                this.name = name;
            }

            @Override public String getName() { return name; }
        }
    }
}
