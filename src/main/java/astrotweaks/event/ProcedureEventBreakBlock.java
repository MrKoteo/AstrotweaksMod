package astrotweaks.event;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.BlockEvent;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.Entity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.Block;
//import net.minecraft.block.material.Material;

import java.util.Random;

import astrotweaks.item.*;
import astrotweaks.world.DepthsDim;

import astrotweaks.ModVariables;


@Mod.EventBusSubscriber(modid = "astrotweaks")
public final class ProcedureEventBreakBlock {
    private static final int CAVERN_DIM_ID = DepthsDim.DIMID;
	public ProcedureEventBreakBlock() {}
	private static void spawnItem(World world, int x, int y, int z, ItemStack stack) {
	  if (world == null || stack == null || stack.isEmpty() || world.isRemote) return;
	  EntityItem ei = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, stack);
	  ei.setPickupDelay(10);
	  world.spawnEntity(ei);
	}
	public static void exect(World world, int x, int y, int z, Entity entity, BlockEvent.BreakEvent event) {
		if (world == null || world.isRemote) return;

		Random rand = world.rand;
		double rng1 = rand.nextDouble();
		double RNM = 0;

		BlockPos pos = new BlockPos(x, y, z);
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		//Material mat = state.getMaterial();

		//ItemStack held = (entity instanceof EntityLivingBase) ? ((EntityLivingBase) entity).getHeldItemMainhand() : ItemStack.EMPTY;
		//boolean notShears = !(held.getItem() == new ItemStack(Items.SHEARS, 1).getItem());

		
		boolean notCreativeMode = true;
		if (entity instanceof EntityPlayer) notCreativeMode = !((EntityPlayer)entity).capabilities.isCreativeMode;
		//net.minecraft.block.Block block = world.getBlockState(pos).getBlock();

		//int dim = world.provider.getDimension();

		//boolean isTallGrassVariant = block == Blocks.TALLGRASS.getStateFromMeta(1).getBlock() || block == Blocks.DOUBLE_PLANT.getStateFromMeta(2).getBlock();

		

		if (notCreativeMode && ModVariables.Extra_Drops_All) {
		boolean isTallGrassVariant = block == Blocks.TALLGRASS && state.getValue(BlockTallGrass.TYPE) == BlockTallGrass.EnumType.GRASS;
		IBlockState AIR = Blocks.AIR.getDefaultState();

		ItemStack held = entity instanceof EntityLivingBase ? ((EntityLivingBase)entity).getHeldItemMainhand() : ItemStack.EMPTY;
		boolean notShears = held.getItem() != Items.SHEARS;
		
		if (isTallGrassVariant && notShears && ModVariables.Extra_Drops_Grass) {
			if (rng1 < 0.14) {
				spawnItem(world, x, y, z, new ItemStack(ATItems.PLANT_FIBER, 1));
			}
			if (rng1 < 0.02 && rand.nextDouble() < 0.51) {
				RNM = rand.nextDouble();
				if (RNM < 0.24) {	spawnItem(world, x, y, z, new ItemStack(Items.POISONOUS_POTATO, 1));} 
				else if (RNM< 0.38){spawnItem(world, x, y, z, new ItemStack(Items.CARROT, 			1));} 
				else if (RNM< 0.51){spawnItem(world, x, y, z, new ItemStack(Items.POTATO, 			1));} 
				else if (RNM< 0.76){spawnItem(world, x, y, z, new ItemStack(Items.WHEAT,			1));} 
				else if (RNM< 0.85){spawnItem(world, x, y, z, new ItemStack(Items.BEETROOT, 		1));} 
				else if (RNM< 0.92){spawnItem(world, x, y, z, new ItemStack(Blocks.BROWN_MUSHROOM,	1));} 
				else if (RNM< 0.97){spawnItem(world, x, y, z, new ItemStack(Blocks.RED_MUSHROOM,	1));} 
				else {				spawnItem(world, x, y, z, new ItemStack(Blocks.TALLGRASS, 		1, 1));}
			}
		} else if (block == Blocks.VINE && notShears) {
			if (rand.nextDouble() < 0.25) {
				spawnItem(world, x, y, z, new ItemStack(ATItems.CORDAGE_VINE, 1));
			}
		} else if (block == Blocks.DIRT || block == Blocks.GRASS) {
			if (rand.nextDouble() < 0.035) {
				world.setBlockState(pos, AIR);
				spawnItem(world, x, y, z, new ItemStack(ATItems.ROCK, 1));
			}
			if (rand.nextDouble() < 0.015) {
				world.setBlockState(pos, AIR);
				spawnItem(world, x, y, z, new ItemStack(ATItems.ROCK_FLAT, 1));
			}
			if (rand.nextDouble() < 0.008) {
				world.setBlockState(pos, AIR);
				spawnItem(world, x, y, z, new ItemStack(Items.FLINT, 1));
			}
		}
		}

		if (world.provider.getDimension() == CAVERN_DIM_ID && block == Blocks.MAGMA) {
			double chance = 0;
			chance = rand.nextDouble();
			if (((chance) < 0.2)) {
				world.setBlockState(pos, Blocks.LAVA.getDefaultState(), 3);
				if (((chance) < 0.1)) {
					Entity entityToSpawn = new EntityMagmaCube(world);

					entityToSpawn.setLocationAndAngles((x + 0.5), (y + 0.5), (z + 0.5), world.rand.nextFloat() * 360F, 0.0F);
					world.spawnEntity(entityToSpawn);
				}
			}
		}
	}
	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event) {
		BlockPos pos = event.getPos();
		exect(event.getWorld(), pos.getX(), pos.getY(), pos.getZ(), event.getPlayer(), event);
	}
}
