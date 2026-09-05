package astrotweaks.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import astrotweaks.procedure.ProcedureCoockedPufferfishEaten;

public final class ItemCoockedPufferfish {
    public static final Item PUFFERFISH = new ItemFood(2, 1f, false) {
        @Override
        public EnumAction getItemUseAction(ItemStack par1ItemStack) {
            return EnumAction.EAT;
        }
        @Override
        protected void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entity) {
            super.onFoodEaten(itemStack, world, entity);
            ProcedureCoockedPufferfishEaten.executeProcedure(entity);
        }
    }.setAlwaysEdible().setCreativeTab(CreativeTabs.FOOD).setMaxStackSize(64).setRegistryName("astrotweaks", "coocked_pufferfish").setUnlocalizedName("coocked_pufferfish");

    private ItemCoockedPufferfish() {}
}