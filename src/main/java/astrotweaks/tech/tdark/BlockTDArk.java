package astrotweaks.tech.tdark;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.network.NetworkManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.SoundType;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.Block;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.DimensionManager;
import net.minecraft.util.text.TextFormatting;



import astrotweaks.creativetab.ATCreativeTabs;
import astrotweaks.AstrotweaksMod;




// Trans-dimensional Ark
public class BlockTDArk {
    // Это пул ID для новых измерений. При создании нового измерения мы берём персое свободное число от P[0] до P[1]. При привышении перезаписываем мир с последним ID в его пуле
    private static final Integer[] NEW_OVERWORLD_DIMENSION_POOL = {100_000, 200_000};



	public static class BlockCustom extends Block implements ITileEntityProvider {
		public BlockCustom() {
			super(Material.IRON);
			setUnlocalizedName("tdark");
			setSoundType(SoundType.METAL);
			setHarvestLevel("pickaxe",5);
			setHardness(1000F);
			setResistance(1000F);
			setLightLevel(0.333333333333F);
			setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);
		}
		@Override public EnumPushReaction getMobilityFlag(IBlockState state) { return EnumPushReaction.BLOCK; }
		@Override public MapColor getMapColor(IBlockState state,IBlockAccess blockAccess,BlockPos pos) { return MapColor.BLACK; }
		@Override public TileEntity createNewTileEntity(World worldIn,int meta) { return new TileEntityCustom(); }
		@Override
		public boolean eventReceived(IBlockState state,World worldIn,BlockPos pos,int eventID,int eventParam) {
			super.eventReceived(state, worldIn, pos, eventID, eventParam);
			TileEntity tileentity = worldIn.getTileEntity(pos);
			return tileentity == null ? false : tileentity.receiveClientEvent(eventID, eventParam);
		}
		@Override public EnumBlockRenderType getRenderType(IBlockState state) { return EnumBlockRenderType.MODEL; }
		@Override
		public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer entity, EnumHand hand, EnumFacing direction, float hitX, float hitY, float hitZ) {
			super.onBlockActivated(world, pos, state, entity, hand, direction, hitX, hitY, hitZ);
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			if (entity instanceof EntityPlayer) {
				((EntityPlayer) entity).openGui(AstrotweaksMod.instance, TDArkGUI.GUIID, world, x, y, z);
			}
			return true;
		}
	}

	public static class TileEntityCustom extends TileEntity implements ITickable {
		// BlockArk
        private int targetSeed;
	    private int targetDim = 0;
	    private int targetX = 0;
	    private int targetY = 65;
	    private int targetZ = 0;
	    private boolean clearMode = true; // true = destroy, false = replace
	    private boolean captureEntities = true;
	    private boolean captureItems = true;
	    private int mv_code = 0;


	    private boolean transferPending = false;
	    private int pendingDim, pendingX, pendingY, pendingZ;
	    private boolean pendingClearMode, pendingCaptureEntities, pendingCaptureItems;
	    private BlockPos terminalPos; // BlockArk pos
	    private EntityPlayerMP triggeringPlayer;

		private final int border = 29999990;
		private final int max_mv_code = 1000;


	    // getters & setters
	    public int getTargetSeed() { return targetSeed; }
		public void setTargetSeed(int seed) {
		    this.targetSeed = seed;
		    markDirty();
			if (world != null && !world.isRemote) {
			    IBlockState state = world.getBlockState(pos);
			    world.notifyBlockUpdate(pos, state, state, 3);
			}
		}
	    public int getTargetDim() { return targetDim; }
		public void setTargetDim(int dim) {
		    this.targetDim = dim;
		    markDirty();
			if (world != null && !world.isRemote) {
			    IBlockState state = world.getBlockState(pos);
			    world.notifyBlockUpdate(pos, state, state, 3);
			}
		}
	    public int getTargetX() { return targetX; }
		public void setTargetX(int x) {
		    this.targetX = x;
		    markDirty();
			if (world != null && !world.isRemote) {
			    IBlockState state = world.getBlockState(pos);
			    world.notifyBlockUpdate(pos, state, state, 3);
			}
		}
	    public int getTargetY() { return targetY; }
		public void setTargetY(int y) {
		    this.targetY = y;
		    markDirty();
			if (world != null && !world.isRemote) {
			    IBlockState state = world.getBlockState(pos);
			    world.notifyBlockUpdate(pos, state, state, 3);
			}
		}
	    public int getTargetZ() { return targetZ; }
		public void setTargetZ(int z) {
		    this.targetZ = z;
		    markDirty();
			if (world != null && !world.isRemote) {
			    IBlockState state = world.getBlockState(pos);
			    world.notifyBlockUpdate(pos, state, state, 3);
			}
		}
	    public boolean getClearMode() { return clearMode; }
	    public void setClearMode(boolean mode) {
	        this.clearMode = mode;
	        markDirty();
	        if (world != null && !world.isRemote) {
	            IBlockState state = world.getBlockState(pos);
	            world.notifyBlockUpdate(pos, state, state, 3);
	        }
	    }
	    public boolean getCaptureEntities() { return captureEntities; }
	    public void setCaptureEntities(boolean capture) {
	        this.captureEntities = capture;
	        markDirty();
	        if (world != null && !world.isRemote) {
	            IBlockState state = world.getBlockState(pos);
	            world.notifyBlockUpdate(pos, state, state, 3);
	        }
	    }
	    public boolean getCaptureItems() { return captureItems; }
	    public void setCaptureItems(boolean capture) {
	        this.captureItems = capture;
	        markDirty();
	        if (world != null && !world.isRemote) {
	            IBlockState state = world.getBlockState(pos);
	            world.notifyBlockUpdate(pos, state, state, 3);
	        }
	    }
	    public int getMVCode() { return mv_code; }
	    public void setDelayTicks(int code) {
	        this.mv_code = Math.min(max_mv_code, Math.max(1000, code)); // limit
	        markDirty();
	        if (world != null && !world.isRemote) {
	            IBlockState state = world.getBlockState(pos);
	            world.notifyBlockUpdate(pos, state, state, 3);
	        }
	    }
		/////
	    public void startDelayedTransfer(EntityPlayerMP player,BlockPos termPos,int dim,int x,int y,int z,boolean clearMode,boolean captureEntities,boolean captureItems,int delay) {
		    BlockPos corePos = TDArkTransferHelper.findCore(world, termPos);
		    if (corePos == null) {
		        player.sendMessage(new TextComponentTranslation("ark.err.structure").setStyle(new Style().setColor(TextFormatting.RED)));
		        return;
		    }
		    if (!DimensionManager.isDimensionRegistered(dim)) {
		        player.sendMessage(new TextComponentTranslation("ark.err.dim", dim).setStyle(new Style().setColor(TextFormatting.RED)));
		        return;
		    }
		    WorldServer targetWorld = player.getServer().getWorld(dim);
		    if (targetWorld == null) {
		        player.sendMessage(new TextComponentTranslation("ark.err.target_world").setStyle(new Style().setColor(TextFormatting.RED)));
		        return;
		    }

		    // this's like in performTeleport
		    int coreTargetY = y + 2;
		    BlockPos corePosTarget = new BlockPos(x, coreTargetY, z);
		    BlockPos targetMin = corePosTarget.add(-3, -2, -3);
		    BlockPos targetMax = corePosTarget.add(3, 2, 3);
		
		    int minY = coreTargetY - 2;
		    int maxY = coreTargetY + 2;
		    if ((minY < 9 || maxY > 247) || (Math.abs(x) > border || Math.abs(z) > border)) {
		        return;
		    }

		    int minChunkX = targetMin.getX() >> 4;
		    int maxChunkX = targetMax.getX() >> 4;
		    int minChunkZ = targetMin.getZ() >> 4;
		    int maxChunkZ = targetMax.getZ() >> 4;
		    for (int cx = minChunkX; cx <= maxChunkX; cx++) {
		        for (int cz = minChunkZ; cz <= maxChunkZ; cz++) {
		            targetWorld.getChunkProvider().provideChunk(cx, cz);
		        }
		    }
	        this.transferPending = true;
	        this.pendingDim = dim;
	        this.pendingX = x;
	        this.pendingY = y;
	        this.pendingZ = z;
	        this.pendingClearMode = clearMode;
	        this.pendingCaptureEntities = captureEntities;
	        this.pendingCaptureItems = captureItems;
	        this.terminalPos = termPos;
	        this.triggeringPlayer = player;
	        markDirty();
	    }
	    @Override
	    public void readFromNBT(NBTTagCompound compound) {
	        super.readFromNBT(compound);
	        // read fields
	        if (compound.hasKey("targetDim")) this.targetDim = compound.getInteger("targetDim");
	        if (compound.hasKey("targetX")) this.targetX = compound.getInteger("targetX");
	        if (compound.hasKey("targetY")) this.targetY = compound.getInteger("targetY");
	        if (compound.hasKey("targetZ")) this.targetZ = compound.getInteger("targetZ");

	        if (compound.hasKey("clearMode")) this.clearMode = compound.getBoolean("clearMode");
	        if (compound.hasKey("captureEntities")) this.captureEntities = compound.getBoolean("captureEntities");
	        if (compound.hasKey("captureItems")) this.captureItems = compound.getBoolean("captureItems");
	        if (compound.hasKey("delayTicks")) this.mv_code = compound.getInteger("mv_code");

	        transferPending = compound.getBoolean("transferPending");
	        pendingDim = compound.getInteger("pendingDim");
	        pendingX = compound.getInteger("pendingX");
	        pendingY = compound.getInteger("pendingY");
	        pendingZ = compound.getInteger("pendingZ");
	        pendingClearMode = compound.getBoolean("pendingClearMode");
	        pendingCaptureEntities = compound.getBoolean("pendingCaptureEntities");
	        pendingCaptureItems = compound.getBoolean("pendingCaptureItems");
	        if (compound.hasKey("terminalPosX")) {
	            terminalPos = new BlockPos(compound.getInteger("terminalPosX"), compound.getInteger("terminalPosY"), compound.getInteger("terminalPosZ"));
	        }
	    }
		@Override
		public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		    compound = super.writeToNBT(compound);
		    compound.setInteger("targetDim", targetDim);
		    compound.setInteger("targetX", targetX);
		    compound.setInteger("targetY", targetY);
		    compound.setInteger("targetZ", targetZ);

	        compound.setBoolean("clearMode", clearMode);
	        compound.setBoolean("captureEntities", captureEntities);
	        compound.setBoolean("captureItems", captureItems);
	        compound.setInteger("mv_code", mv_code);

	        compound.setBoolean("transferPending", transferPending);
	        compound.setInteger("pendingDim", pendingDim);
	        compound.setInteger("pendingX", pendingX);
	        compound.setInteger("pendingY", pendingY);
	        compound.setInteger("pendingZ", pendingZ);
	        compound.setBoolean("pendingClearMode", pendingClearMode);
	        compound.setBoolean("pendingCaptureEntities", pendingCaptureEntities);
	        compound.setBoolean("pendingCaptureItems", pendingCaptureItems);
	        if (terminalPos != null) {
	            compound.setInteger("terminalPosX", terminalPos.getX());
	            compound.setInteger("terminalPosY", terminalPos.getY());
	            compound.setInteger("terminalPosZ", terminalPos.getZ());
	        }
		    return compound;
		}
		@Override
		public SPacketUpdateTileEntity getUpdatePacket() {
		    NBTTagCompound nbt = new NBTTagCompound();
		    this.writeToNBT(nbt);
		    return new SPacketUpdateTileEntity(this.pos, 0, nbt);
		}
		@Override
		public NBTTagCompound getUpdateTag() {
		    return this.writeToNBT(new NBTTagCompound());
		}
		@Override
		public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		    this.readFromNBT(pkt.getNbtCompound());
		    if (world != null) {
		        IBlockState state = world.getBlockState(pos);
		        world.notifyBlockUpdate(pos, state, state, 3);
		    }
		}
		@Override
		public void handleUpdateTag(NBTTagCompound tag) {
		    super.handleUpdateTag(tag);
		    this.readFromNBT(tag);
		}
	    @Override
	    public void update() {
	        if (world.isRemote) return;

	        if (transferPending) {

                transferPending = false;
                if (triggeringPlayer == null || triggeringPlayer.isDead) {
                    return;
                }
                // check structure
                BlockPos currentCore = TDArkTransferHelper.findCore(world, terminalPos);
                if (currentCore == null) {
                    return;
                }
                // execute
                TDArkTransferHelper.performTeleport(triggeringPlayer, world, terminalPos, this, pendingDim, pendingX, pendingY, pendingZ,
                        pendingClearMode, pendingCaptureEntities, pendingCaptureItems);
                triggeringPlayer = null;
            }
            markDirty();
	        
	    }
	}
}

