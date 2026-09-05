package astrotweaks.item;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import astrotweaks.creativetab.ATCreativeTabs;
import astrotweaks.procedure.ProcedureSQInvTick;

public final class ItemYAntiQuant {
    public static final Item Y_ANTI_QUANT = new Item() {
        @Override
        public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag) {
            super.addInformation(itemstack, world, list, flag);
            list.add("???");
        }
        @Override
        public void onUpdate(ItemStack itemstack, World world, Entity entity, int slot, boolean par5) {
            super.onUpdate(itemstack, world, entity, slot, par5);
            ProcedureSQInvTick.exect(entity, itemstack);
        }
    }.setCreativeTab(ATCreativeTabs.AT_INTEGRATION_CT).setRegistryName("astrotweaks", "y_anti_quant").setUnlocalizedName("y_anti_quant");

    private ItemYAntiQuant() {}
}
