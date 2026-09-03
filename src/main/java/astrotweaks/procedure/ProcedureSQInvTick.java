package astrotweaks.procedure;

import net.minecraftforge.items.ItemHandlerHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;

import astrotweaks.item.ItemNullQuant;



public class ProcedureSQInvTick  {
	public ProcedureSQInvTick() {}

	public static void executeProcedure(Entity entity, ItemStack itemstack) {
		if (entity == null) {
			System.err.println("Failed to load dependency entity for procedure SQInvTick!");
			return;
		}
		if (itemstack == null) {
			System.err.println("Failed to load dependency itemstack for procedure SQInvTick!");
			return;
		}
		if ((!((entity instanceof EntityPlayer) ? ((EntityPlayer) entity).capabilities.isCreativeMode : false))) {
			if ((Math.random() < 0.2)) {
				if (entity instanceof EntityPlayer)
					((EntityPlayer) entity).inventory.clearMatchingItems((itemstack).getItem(), -1, (int) 1, null);
				if (entity instanceof EntityPlayer) {
					ItemStack _setstack = new ItemStack(ItemNullQuant.block, (int) (1));
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(((EntityPlayer) entity), _setstack);
				}
			}
		}
	}
}
