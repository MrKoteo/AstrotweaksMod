package astrotweaks.procedure;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.block.state.IBlockState;

import java.util.List;

import astrotweaks.recipe.GavelRecipeRegistry;
import astrotweaks.recipe.GavelRecipe;



public class ProcedureGavelRightClickedOnBlock {
    public ProcedureGavelRightClickedOnBlock() {}

    public static void executeProcedure(int x, int y, int z, World world, Entity entity, ItemStack itemstack) {

        // check mainhand has contain Gavel
        boolean holdingGavel = ((entity instanceof EntityLivingBase) ? ((EntityLivingBase) entity).getHeldItemMainhand() : ItemStack.EMPTY).getItem() == new ItemStack(astrotweaks.item.ItemGavel.GAVEL, 1).getItem();
        if (!holdingGavel) return;

        IBlockState state = world.getBlockState(new BlockPos(x, y, z));
        List<GavelRecipe> recipes = GavelRecipeRegistry.getRecipes();
        for (GavelRecipe r : recipes) {
            if (!r.matches(state)) continue;

            // damage Gavel
            itemstack.setItemDamage(itemstack.getItemDamage() + 1);

            // remove block
            world.setBlockState(new BlockPos(x, y, z), net.minecraft.init.Blocks.AIR.getDefaultState(), 3);

            // spawn items for chances
            for (int i = 0; i < r.outputs.length; i++) {
                ItemStack outTemplate = r.outputs[i];
                float chance = r.chances[i];
                if (world.isRemote) continue;
                if (Math.random() <= chance) {
                    // copy, for don't mutate template
                    ItemStack toSpawn = outTemplate.copy();
                    EntityItem ei = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, toSpawn);
                    ei.setPickupDelay(10);
                    world.spawnEntity(ei);
                }
            }
            break; // stop after first recipe match
        }
    }
}
