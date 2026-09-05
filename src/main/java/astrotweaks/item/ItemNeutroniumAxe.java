package astrotweaks.item;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.util.EnumHelper;

import astrotweaks.creativetab.ATCreativeTabs;

public final class ItemNeutroniumAxe {
    public static final Item AXE = new ItemToolCustom() {
    }.setRegistryName("astrotweaks", "neutronium_axe").setUnlocalizedName("neutronium_axe").setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);

    private ItemNeutroniumAxe() {}
    private static class ItemToolCustom extends ItemTool {
        private static final Set<Block> effective_items_set = com.google.common.collect.Sets
                .newHashSet(new Block[]{Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN,
                        Blocks.MELON_BLOCK, Blocks.LADDER, Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE});
        protected ItemToolCustom() {
            super(EnumHelper.addToolMaterial("NEUTRONIUM_AXE", 6, 100000, 48f, 299f, 1), effective_items_set);
            this.attackDamage = 299f;
            this.attackSpeed = -2.9f;
        }

        @Override
        public float getDestroySpeed(ItemStack stack, IBlockState state) {
            Material material = state.getMaterial();
            return material != Material.WOOD && material != Material.PLANTS && material != Material.VINE
                    ? super.getDestroySpeed(stack, state)
                    : this.efficiency;
        }
    }
}