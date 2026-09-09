package astrotweaks.tech.tdark;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.server.MinecraftServer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.entity.EntityLivingBase;

import net.minecraft.server.management.PlayerChunkMap;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.network.play.server.SPacketUnloadChunk;

import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.network.play.server.SPacketSpawnMob;
import net.minecraft.network.play.server.SPacketEntityMetadata;
import net.minecraft.network.play.server.SPacketEntityTeleport;
import net.minecraft.network.play.server.SPacketEntityHeadLook;
import net.minecraft.network.play.server.SPacketEntityEquipment;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketSpawnExperienceOrb;
import net.minecraft.network.play.server.SPacketSpawnPainting;
import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.Teleporter;

import astrotweaks.tech.ATTechnologies;
import astrotweaks.tech.qts.SuppressorManager;

import java.util.ArrayList;
import java.util.List;




public class TDArkTransferHelper {
    // Helper class for saving blocks
    public static class BlockSave {
        public IBlockState state;
        public NBTTagCompound teNBT;
        public BlockSave(IBlockState state, NBTTagCompound teNBT) {
            this.state = state;
            this.teNBT = teNBT;
        }
    }
    // Custom Teleporter for entity relocation
    public static class CustomTeleporter extends Teleporter {
        private final double offsetX, offsetY, offsetZ;
        private final double targetCoreX, targetCoreY, targetCoreZ;

        public CustomTeleporter(WorldServer world, double targetCoreX, double targetCoreY, double targetCoreZ, double offsetX, double offsetY, double offsetZ) {
            super(world);
            this.targetCoreX = targetCoreX;
            this.targetCoreY = targetCoreY;
            this.targetCoreZ = targetCoreZ;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.offsetZ = offsetZ;
        }

        @Override
        public void placeEntity(World world, Entity entity, float currentPortalY) {
		    double newX = targetCoreX + offsetX;
		    double newY = targetCoreY + offsetY;
		    double newZ = targetCoreZ + offsetZ;

            entity.setPositionAndUpdate(newX, newY, newZ);
            entity.motionX = 0.0D;
            entity.motionY = 0.0D;
            entity.motionZ = 0.0D;

            // Preserve rotation (already on the entity)
			if (entity instanceof EntityLivingBase) {
			    EntityLivingBase elb = (EntityLivingBase) entity;
			    elb.setRotationYawHead(elb.rotationYaw);
			    elb.prevRotationYawHead = elb.rotationYaw;
			    elb.setRenderYawOffset(elb.rotationYaw);
			}
        }

        @Override
        public boolean isVanilla() {
            return false; // Custom teleporter
        }
    }

    public static void performTeleport(EntityPlayerMP player,World world,BlockPos termPos,BlockTDArk.TileEntityCustom teArk,int targetDim,int targetX,int targetY,int targetZ,
					boolean clearMode, boolean captureEntities, boolean captureItems) {
		//////////

	    BlockPos corePos = findCore(world, termPos);
	    if (corePos == null) {
	        player.sendMessage(new TextComponentTranslation("ark.err.structure"));
	        return;
	    }
	    if (!DimensionManager.isDimensionRegistered(targetDim)) {
	        player.sendMessage(new TextComponentTranslation("ark.err.dim", targetDim));
	        return;
	    }
	    
		// ########## Checking the suppressor in the source world
		if (SuppressorManager.isPositionBlocked(world, corePos)) {
		    player.sendMessage(new TextComponentTranslation("qts.no_tp"));
		    return;
		}

        // 3. Checking the boundaries of the world (taking into account Y+2 for the kernel)
        int coreTargetY = targetY + 2; // Y coord of the core in the new location
        int minY = coreTargetY - 2;
        int maxY = coreTargetY + 2;
        final int border = 29999990;
        if ((minY < 9 || maxY > 247) || (Math.abs(targetX) > border || Math.abs(targetZ) > border)) {
            player.sendMessage(new TextComponentTranslation("ark.err.aow"));
            return;
        }
        // Check X,Z boundaries (standard)

        // 4. Get target world
	    WorldServer targetWorld = player.getServer().getWorld(targetDim);
	    if (targetWorld == null) {
	        player.sendMessage(new TextComponentTranslation("ark.err.target_world"));
	        return;
	    }

        // 5. Defining areas
	    BlockPos corePosTarget = new BlockPos(targetX, coreTargetY, targetZ);
	    BlockPos sourceMin = corePos.add(-7, -7, -7);
	    BlockPos sourceMax = corePos.add(7, 7, 7);
	    BlockPos targetMin = corePosTarget.add(-7, -7, -7);
	    BlockPos targetMax = corePosTarget.add(7, 7, 7);
        // Переносим куб 15x15x15 блоков
		
		if (isSuppressorInArea(targetWorld, targetMin, targetMax)) {
		    player.sendMessage(new TextComponentTranslation("qts.tp_interrupted")); // Целевая область защищена подавителем
		    return;
		}

		// ########## Checking the suppressor in the target world (after receiving targetWorld and corePosTarget)
		if (SuppressorManager.isPositionBlocked(targetWorld, corePosTarget)) {
		    player.sendMessage(new TextComponentTranslation("qts.no_tp_target"));
		    return;
		}

        // 6. Check for unbreakable blocks in the target area
        for (BlockPos p : BlockPos.getAllInBoxMutable(targetMin, targetMax)) {
            IBlockState state = targetWorld.getBlockState(p);
            if (state.getBlockHardness(targetWorld, p) < 0) { // hardness < 0 => unbreakable
            	System.out.println("[TDArk] ERROR: Unbreakable block at " + p);
                player.sendMessage(new TextComponentTranslation("ark.err.unbreakable_target",  state.getBlock().getLocalizedName()));
                return;
            }
        }
        // Target area clear of unbreakable blocks


        // 7. Save blocks and TileEntity from the source
	    List<BlockSave> blocksToMove = new ArrayList<>();
	    for (BlockPos p : BlockPos.getAllInBoxMutable(sourceMin, sourceMax)) {
	        IBlockState state = world.getBlockState(p);
	        TileEntity te = world.getTileEntity(p);
	        NBTTagCompound teNBT = null;
	        if (te != null) {
	            teNBT = te.serializeNBT();
	            teNBT.removeTag("x");
	            teNBT.removeTag("y");
	            teNBT.removeTag("z");
	        }
	        blocksToMove.add(new BlockSave(state, teNBT));
	    }

        // 8. Collect entities in the area (except for players)
	    List<Entity> entitiesInArea = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(sourceMin, sourceMax.add(1, 1, 1)));
	    List<Entity> entitiesToTransfer = new ArrayList<>();
	    List<EntityPlayer> playersInArea = new ArrayList<>();
	    for (Entity entity : entitiesInArea) {
		    boolean isItem = entity instanceof EntityItem;
		    if (isItem && !captureItems) continue;
		    if (!(entity instanceof EntityPlayer) && !isItem && !captureEntities) continue;

	        if (entity instanceof EntityPlayer) {
	            playersInArea.add((EntityPlayer) entity);
	        } else {
	            entitiesToTransfer.add(entity);
	        }
	    }

		// Loading chunks of the target area
	    int minChunkX = targetMin.getX() >> 3;
	    int maxChunkX = targetMax.getX() >> 3;
	    int minChunkZ = targetMin.getZ() >> 3;
	    int maxChunkZ = targetMax.getZ() >> 3;
	    for (int cx = minChunkX; cx <= maxChunkX; cx++) {
	        for (int cz = minChunkZ; cz <= maxChunkZ; cz++) {
	            targetWorld.getChunkProvider().provideChunk(cx, cz);
	        }
	    }

        // 9. Clear the target area (destroy all blocks)
        if (clearMode) {
            // destroy mode
            for (BlockPos p : BlockPos.getAllInBoxMutable(targetMin, targetMax)) {
                targetWorld.destroyBlock(p, true);
            }
        } else {
            // replace mode
            for (BlockPos p : BlockPos.getAllInBoxMutable(targetMin, targetMax)) {
                targetWorld.destroyBlock(p, false);
            }
        }

        // 10. Place blocks in the target area
	    int index = 0;
	    for (BlockPos p : BlockPos.getAllInBoxMutable(targetMin, targetMax)) {
	        BlockSave save = blocksToMove.get(index++);
	        IBlockState state = save.state;

		    // 1) place block
		    targetWorld.setBlockState(p, state, 3);

		    // 2) If there is NBT, restore TE correctly:
		    if (save.teNBT != null) {
		        // Insert coordinates into NBT (VERY IMPORTANT)
		        save.teNBT.setInteger("x", p.getX());
		        save.teNBT.setInteger("y", p.getY());
		        save.teNBT.setInteger("z", p.getZ());

		        // Delete the existing TE (if any)
		        targetWorld.removeTileEntity(p);

		        // Create a new TE from NBT
	            TileEntity newTe = TileEntity.create(targetWorld, save.teNBT);
	            if (newTe != null) {
	                targetWorld.setTileEntity(p, newTe);
	                newTe.validate();
	                newTe.markDirty();
	            }
		    }
			// Explicitly synchronize all TileEntities with client	
		    // Notify the world a bit more strongly (instead of just markBlockRangeForRenderUpdate)
	        targetWorld.notifyBlockUpdate(p, state, state, 3);
	        if (targetWorld instanceof WorldServer) {
	            ((WorldServer) targetWorld).getPlayerChunkMap().markBlockForUpdate(p);
	        }
		}

		// 12. Teleport players
		int sourceDim = world.provider.getDimension();
		for (EntityPlayer playerX : playersInArea) {
		    if (playerX instanceof EntityPlayerMP) {
		        EntityPlayerMP mp = (EntityPlayerMP) playerX;

		        double newX = corePosTarget.getX() + (playerX.posX - corePos.getX());
		        double newY = corePosTarget.getY() + (playerX.posY - corePos.getY() + 0.1);
		        double newZ = corePosTarget.getZ() + (playerX.posZ - corePos.getZ());


		        if (sourceDim == targetDim) {
		            // Same dimension - just move it
		            mp.setPositionAndUpdate(newX, newY, newZ);

		            // Synchronize head rotation (like entities)
		            mp.setRotationYawHead(mp.rotationYaw);
		            mp.prevRotationYawHead = mp.rotationYaw;
		            mp.setRenderYawOffset(mp.rotationYaw);
		        } else {
		            // Different dimensions - use the standard method
		            MinecraftServer server = player.getServer();
		            server.getPlayerList().transferPlayerToDimension(mp, targetDim, 
		                new TeleporterDirectWrapper(targetDim, server, newX, newY, newZ));
		        }
		    }
		}

        // 11. Transfer entities with dimension check
        //int sourceDim = world.provider.getDimension();
        for (Entity entity : entitiesToTransfer) {
            double dx = entity.posX - corePos.getX();
            double dy = entity.posY - corePos.getY();
            double dz = entity.posZ - corePos.getZ();
            double newX = corePosTarget.getX() + dx;
            double newY = corePosTarget.getY() + dy;
            double newZ = corePosTarget.getZ() + dz;

            boolean success = false;
            if (sourceDim == targetDim) {
                // Same dimension: direct reposition (no changeDimension to avoid UUID conflict)
		        entity.setPositionAndUpdate(newX, newY, newZ);
		        entity.motionX = 0.0D;
		        entity.motionY = 0.0D;
		        entity.motionZ = 0.0D;

		        if (entity instanceof EntityLivingBase) {
		            EntityLivingBase living = (EntityLivingBase) entity;
		            living.setRotationYawHead(living.rotationYaw);
		            living.prevRotationYawHead = living.rotationYaw;
		            living.setRenderYawOffset(living.rotationYaw);
		        }
                if (targetWorld instanceof WorldServer) {
                    ((WorldServer) targetWorld).getPlayerChunkMap().markBlockForUpdate(new BlockPos(newX, newY, newZ)); // Update chunk
                }
		        success = true;
		        System.out.println("[TDArk] Repositioned entity in same dim: " + entity);
		    } else {
                // Different dimension: use changeDimension with default teleporter, then adjust pos
                // Temporarily set pos in entity before transfer (teleporter will override if custom, but we use default)
                //entity.setPositionAndUpdate(newX, newY, newZ); // Pre-set for safety
				CustomTeleporter teleporter = new CustomTeleporter(targetWorld, corePosTarget.getX(), corePosTarget.getY(), corePosTarget.getZ(), dx, dy, dz);
				Entity newEntity = entity.changeDimension(targetDim, teleporter);
				if (newEntity != null) {
				    newEntity.motionX = 0.0D;
				    newEntity.motionY = 0.0D;
				    newEntity.motionZ = 0.0D;
				    if (newEntity instanceof EntityLivingBase) {
				        EntityLivingBase living = (EntityLivingBase) newEntity;
				        living.setRotationYawHead(living.rotationYaw);
				        living.prevRotationYawHead = living.rotationYaw;
				        living.setRenderYawOffset(living.rotationYaw);
		            }
                } else {
                    System.out.println("[TDArk] Failed to transfer entity to different dim: " + entity);
                }
            }
            if (!success) {
                System.out.println("[TDArk] Entity transfer failed: " + entity);
            }
        }

        // 13. Remove blocks in the source (replace with air)
        for (BlockPos p : BlockPos.getAllInBoxMutable(sourceMin, sourceMax)) {
		    if (world.getTileEntity(p) != null) {
		        world.removeTileEntity(p);
		    }
            world.setBlockState(p, Blocks.AIR.getDefaultState(), 3);
            world.getChunkFromBlockCoords(p).markDirty();
        }
        // Notify the player
        player.sendMessage(new TextComponentTranslation("ark.success"));

    }

	// Search for core structure (QM block and resonator) 
	public static BlockPos findCore(World world, BlockPos termPos) {
	    BlockPos up1 = termPos.up();       // +1
	    BlockPos up2 = termPos.up(2);      // +2
	    BlockPos down1 = termPos.down();  // -1
	    BlockPos down2 = termPos.down(2);  // -2

	    IBlockState stateUp1 = world.getBlockState(up1);
	    IBlockState stateUp2 = world.getBlockState(up2);
	    IBlockState stateDown1 = world.getBlockState(down1);
	    IBlockState stateDown2 = world.getBlockState(down2);
	    Block qmBlock = Block.getBlockFromName("astrotweaks:qm_block");
	    Block resBlock = Block.getBlockFromName("astrotweaks:ark_resonator");
	    if (stateUp1.getBlock() == qmBlock && stateUp2.getBlock() == resBlock) {
	        return up1;
	    }
	    if (stateDown1.getBlock() == qmBlock && stateDown2.getBlock() == resBlock) {
	        return down1;
	    }
	    return null;
	}

    private static class TeleporterDirectWrapper extends Teleporter {
        private final double wx, wy, wz;
        public TeleporterDirectWrapper(int dimension, MinecraftServer server, double wx, double wy, double wz) {
            super(server.getWorld(dimension));
            this.wx = wx;
            this.wy = wy;
            this.wz = wz;
        }
        @Override public void placeInPortal(Entity entity,float yawRotation) { entity.setPosition(wx, wy, wz); }
        @Override public boolean placeInExistingPortal(Entity entity,float yawRotation) { return true; }
        @Override public boolean makePortal(Entity entity) { return true; }
    }
	private static boolean isSuppressorInArea(World world, BlockPos min, BlockPos max) {
	    for (BlockPos p : BlockPos.getAllInBoxMutable(min, max)) {
	        if (world.getBlockState(p).getBlock() == ATTechnologies.QTP_SUPRESSOR) {
	            return true;
	        }
	    }
	    return false;
	}
}
