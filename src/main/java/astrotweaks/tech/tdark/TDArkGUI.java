package astrotweaks.tech.tdark;

import org.lwjgl.input.Keyboard;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.nbt.NBTTagCompound;

import io.netty.buffer.ByteBuf;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.io.IOException;

import astrotweaks.AstrotweaksMod;




public class TDArkGUI {
	public static int GUIID = 20;
	public static HashMap guistate = new HashMap();

	// Message for transferring data from the GUI
	public static class TDArkActionMessage implements IMessage {
	    int buttonID;
	    int blockX, blockY, blockZ;
	    String dimStr;
	    private int delayTicks;
        private String SeedStr;
	    String xStr, yStr, zStr, delay;
	    private boolean clearMode;
	    private boolean captureEntities;
	    private boolean captureItems;


	    public TDArkActionMessage() {}

		public TDArkActionMessage(int buttonID, int blockX, int blockY, int blockZ, String delayTicks, String seed, String x, String y, String z,
 					boolean clearMode, boolean captureEntities, boolean captureItems) {
	        this.buttonID = buttonID;
	        this.blockX = blockX; this.blockY = blockY; this.blockZ = blockZ;
	        this.SeedStr = seed; this.xStr = x; this.yStr = y; this.zStr = z; this.delay = delayTicks;
	        this.clearMode = clearMode;
	        this.captureEntities = captureEntities;
	        this.captureItems = captureItems;
	    }
	    @Override
	    public void toBytes(ByteBuf buf) {
	        buf.writeInt(buttonID);
	        buf.writeInt(blockX); buf.writeInt(blockY); buf.writeInt(blockZ);
	        writeString(buf, SeedStr); writeString(buf, xStr); writeString(buf, yStr); writeString(buf, zStr);
	        buf.writeBoolean(clearMode);
	        buf.writeBoolean(captureEntities);
	        buf.writeBoolean(captureItems);
	        writeString(buf, delay);
	    }
	    @Override
	    public void fromBytes(ByteBuf buf) {
	        buttonID = buf.readInt();
	        blockX = buf.readInt(); blockY = buf.readInt(); blockZ = buf.readInt();
	        SeedStr = readString(buf); xStr = readString(buf); yStr = readString(buf); zStr = readString(buf);
	        clearMode = buf.readBoolean();
	        captureEntities = buf.readBoolean();
	        captureItems = buf.readBoolean();
	        delay = readString(buf);
	    }
	    private void writeString(ByteBuf buf, String s) {
	        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
	        buf.writeInt(bytes.length);
	        buf.writeBytes(bytes);
	    }
	    private String readString(ByteBuf buf) {
	        int len = buf.readInt();
	        byte[] bytes = new byte[len];
	        buf.readBytes(bytes);
	        return new String(bytes, StandardCharsets.UTF_8);
	    }
	}

	// Message handler
	public static class TDArkActionMessageHandler implements IMessageHandler<TDArkActionMessage, IMessage> {
	    @Override
	    public IMessage onMessage(TDArkActionMessage message, MessageContext context) {
	        EntityPlayerMP player = context.getServerHandler().player;
	        player.getServerWorld().addScheduledTask(() -> {
	            World world = player.world;
	            BlockPos pos = new BlockPos(message.blockX, message.blockY, message.blockZ);
	            if (!world.isBlockLoaded(pos)) return;

	            TileEntity te = world.getTileEntity(pos);
	            if (!(te instanceof BlockTDArk.TileEntityCustom)) return;
	            BlockTDArk.TileEntityCustom teTDArk = (BlockTDArk.TileEntityCustom) te;

	            // Parse strings
	            int targetDim = 0, targetSeed = 0, targetX = 0, targetY = 65, targetZ = 0, delayTicks = 0;
	            boolean parseOk = true;
	            try {
                    targetSeed = Integer.parseInt(message.SeedStr);
	                targetDim = Integer.parseInt(message.dimStr);
	                targetX = Integer.parseInt(message.xStr);
	                targetY = Integer.parseInt(message.yStr);
	                targetZ = Integer.parseInt(message.zStr);
	                delayTicks = Integer.parseInt(message.delay);
	            } catch (NumberFormatException e) {
	                parseOk = false;
	                if (message.buttonID == 0) {
	                    player.sendMessage(new TextComponentTranslation("ark.invalid_coords"));
	                }
	            }
	
				//System.out.println("[TDArk] Received message: buttonID=" + message.buttonID + ", dim=" + message.dimStr + ", x=" + message.xStr + ", y=" + message.yStr + ", z=" + message.zStr + ", delay=" + message.delay + ", parseOk=" + parseOk);

	            if (message.buttonID == 1) {
	                
	                if (parseOk) {
                        teTDArk.setTargetSeed(targetSeed);
	                    //teTDArk.setTargetDim(targetDim);
	                    teTDArk.setTargetX(targetX);
	                    teTDArk.setTargetY(targetY);
	                    teTDArk.setTargetZ(targetZ);
                        
	                }
	                teTDArk.setClearMode(message.clearMode);
	                teTDArk.setCaptureEntities(message.captureEntities);
	                teTDArk.setCaptureItems(message.captureItems);
	                teTDArk.setDelayTicks(delayTicks);
	                return;
	            }

				// Start transfer with delay
	            if (message.buttonID == 0 && parseOk) {
	                //if (delayTicks > 0) {
	                // delay with TileEntity (ITickable)
					if (delayTicks < 5) { delayTicks = 5;}
					if (!(delayTicks == 5)) { player.sendMessage(new TextComponentTranslation(TextFormatting.AQUA + "ark.delayed_start", delayTicks)); }

                    teTDArk.startDelayedTransfer(player, pos, targetSeed, targetX,targetY,targetZ, message.clearMode, message.captureEntities, message.captureItems, delayTicks);

	            }
	        });
	        return null;
	    }
	}

	public static class GuiWindow extends GuiScreen {
	    private World world;
	    private int x, y, z;
	    private EntityPlayer entity;
	    private BlockTDArk.TileEntityCustom teTDArk;
	    private GuiTextField TargetDimID;

	    private boolean clearMode = true;
	    private boolean captureEntities = true;
	    private boolean captureItems = true;
	    //private int delayTicks = 0;

	    private GuiButton btnClearMode;
	    private GuiButton btnCaptureEntities;
	    private GuiButton btnCaptureItems;

        private GuiTextField TargetSeed;
	    private GuiTextField TW_X;
	    private GuiTextField TW_Y;
	    private GuiTextField TW_Z;
	    private GuiTextField mv_code;
	    private static final ResourceLocation texture = new ResourceLocation("astrotweaks:textures/ark_gui_modern.png");

		public GuiWindow(World world, int x, int y, int z, EntityPlayer entity) {
			this.world = world;
			this.x = x; this.y = y; this.z = z;
			this.entity = entity;
		}
		private int k60;
	    @Override
	    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
	        this.drawDefaultBackground();
	        // Draw the background texture
	        this.mc.renderEngine.bindTexture(texture);
	        int k = (this.width - 200) / 2;
	        int l = (this.height - 140) / 2;
	        drawModalRectWithCustomSizedTexture(k, l, 0, 0, 200, 140, 200, 140);
	        // Draw text fields and buttons
	        super.drawScreen(mouseX, mouseY, partialTicks);
	        TargetDimID.drawTextBox();
	        TW_X.drawTextBox();
	        TW_Y.drawTextBox();
	        TW_Z.drawTextBox();
	        mv_code.drawTextBox();
	        // Draw the title
	        fontRenderer.drawString(I18n.format("ark.interface"), k + 32, l + 5, 0x8020FF);

			// Добавить подсказки рядом с полями вввода
			k60 = k + 60;
			fontRenderer.drawString("Seed", k60, l + 22, 0xEEEEEE);
			fontRenderer.drawString("X", k60, l + 46, 0xEEEEEE);
			fontRenderer.drawString("Y", k60, l + 70, 0xEEEEEE);
			fontRenderer.drawString("Z", k60, l + 94, 0xEEEEEE);
			fontRenderer.drawString("Delay", k60, l + 118,0xEEEEEE);
	    }
	    @Override
	    public void updateScreen() {
	        super.updateScreen();
	        TargetSeed.updateCursorCounter();
	        TW_X.updateCursorCounter();
	        TW_Y.updateCursorCounter();
	        TW_Z.updateCursorCounter();
	        mv_code.updateCursorCounter();
	    }
	    @Override
	    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
	        super.mouseClicked(mouseX, mouseY, mouseButton);
	        //int k = (this.width - 200) / 2;
	        //int l = (this.height - 140) / 2;
		    TargetSeed.mouseClicked(mouseX, mouseY, mouseButton);
		    TW_X.mouseClicked(mouseX, mouseY, mouseButton);
		    TW_Y.mouseClicked(mouseX, mouseY, mouseButton);
		    TW_Z.mouseClicked(mouseX, mouseY, mouseButton);
		    mv_code.mouseClicked(mouseX, mouseY, mouseButton);
	    }
	    @Override
	    protected void keyTyped(char typedChar, int keyCode) throws IOException {
	        if (TargetSeed.textboxKeyTyped(typedChar, keyCode)) return;
	        if (TW_X.textboxKeyTyped(typedChar, keyCode)) return;
	        if (TW_Y.textboxKeyTyped(typedChar, keyCode)) return;
	        if (TW_Z.textboxKeyTyped(typedChar, keyCode)) return;
	        if (mv_code.textboxKeyTyped(typedChar, keyCode)) return;
	        super.keyTyped(typedChar, keyCode);
	    }

		private int k11;
		private int k90;
	    @Override
	    public void initGui() {
	        super.initGui();
	        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
	        if (te instanceof BlockTDArk.TileEntityCustom) {
	            teTDArk = (BlockTDArk.TileEntityCustom) te;
	        }
	        int k = (this.width - 200) / 2;
	        int l = (this.height - 140) / 2;
	        Keyboard.enableRepeatEvents(true);
	        buttonList.clear();
		
			// ну типа TE всегда определён

			clearMode = teTDArk.getClearMode();
			captureEntities = teTDArk.getCaptureEntities();
			captureItems = teTDArk.getCaptureItems();

			k11 = k + 11;
			k90 = k + 90;


		    TW_X = new GuiTextField(1, fontRenderer, k11, l + 20, 45, 12);
		    TW_X.setMaxStringLength(8);
		    TW_X.setText(teTDArk != null ? String.valueOf(teTDArk.getTargetX()) : "0");

		    TW_Y = new GuiTextField(2, fontRenderer, k11, l + 44, 45, 12);
		    TW_Y.setMaxStringLength(8);
		    TW_Y.setText(teTDArk != null ? String.valueOf(teTDArk.getTargetY()) : "65");

		    TW_Z = new GuiTextField(3, fontRenderer, k11, l + 68, 45, 12);
		    TW_Z.setMaxStringLength(8);
		    TW_Z.setText(teTDArk != null ? String.valueOf(teTDArk.getTargetZ()) : "0");

		    mv_code = new GuiTextField(4, fontRenderer, k11, l + 92, 45, 12);
		    mv_code.setMaxStringLength(4);
		    mv_code.setText(teTDArk != null ? String.valueOf(teTDArk.getMVCode()) : "0");

		    TargetSeed = new GuiTextField(0, fontRenderer, k11, l + 116, 90, 12);
		    TargetSeed.setMaxStringLength(16);
		    TargetSeed.setText(teTDArk != null ? String.valueOf(teTDArk.getTargetSeed()) : "0");

		    btnClearMode = new GuiButton(1, k90, l + 20, 50, 18, getClearModeText());
		    buttonList.add(btnClearMode);

		    btnCaptureEntities = new GuiButton(2, k90, l + 44, 50, 18, getCaptureEntitiesText());
		    buttonList.add(btnCaptureEntities);

		    btnCaptureItems = new GuiButton(3, k90, l + 68, 50, 18, getCaptureItemsText());
		    buttonList.add(btnCaptureItems);




			updateButtonsDisplay();

	        buttonList.add(new GuiButton(0, k + 150, l + 58, 36, 20, I18n.format("ark.send")));
	    }

	    private String getClearModeText() {
	        return clearMode ? "Destroy" : "Replace";
	    }
	    private String getCaptureEntitiesText() {
	        return captureEntities ? "Entities: ON" : "Entities: OFF";
	    }
	    private String getCaptureItemsText() {
	        return captureItems ? "Items: ON" : "Items: OFF";
	    }
		/*
		private int parseIntSafe(String s, int defaultValue) {
		    try {
		        return Integer.parseInt(s);
		    } catch (NumberFormatException e) {
		        return defaultValue;
		    }
		}*/
		private void updateButtonsDisplay() {
		    if (btnClearMode != null) btnClearMode.displayString = getClearModeText();
		    if (btnCaptureEntities != null) btnCaptureEntities.displayString = getCaptureEntitiesText();
		    if (btnCaptureItems != null) btnCaptureItems.displayString = getCaptureItemsText();
		}
	    @Override
	    protected void actionPerformed(GuiButton button) throws IOException {
	        if (button.id == 0) { // SEND
	            mc.player.closeScreen();
	            AstrotweaksMod.PACKET_HANDLER.sendToServer(new TDArkActionMessage(
	                0, x, y, z,
	                TW_X.getText(),
	                TW_Y.getText(),
	                TW_Z.getText(),
	                mv_code.getText(),
	                TargetSeed.getText(),
	                clearMode,
	                captureEntities,
	                captureItems
	            ));
	        } else if (button.id == 1) { // Clear Mode toggle
	            clearMode = !clearMode;
	            btnClearMode.displayString = getClearModeText();
	        } else if (button.id == 2) { // Entities toggle
	            captureEntities = !captureEntities;
	            if (!captureEntities) {
	                captureItems = false; // automatically turn off items
	                btnCaptureItems.displayString = getCaptureItemsText();
	            }
	            btnCaptureEntities.displayString = getCaptureEntitiesText();
	        } else if (button.id == 3) { // Items toggle
	            if (captureEntities) { // can only be switched if entities are enabled
	                captureItems = !captureItems;
	                btnCaptureItems.displayString = getCaptureItemsText();
	            }
	        }
	    }
	    @Override
	    public void onGuiClosed() {
	        super.onGuiClosed();
	        AstrotweaksMod.PACKET_HANDLER.sendToServer(new TDArkActionMessage(
	            1, x, y, z,
	            TW_X.getText(),
	            TW_Y.getText(),
	            TW_Z.getText(),
	            mv_code.getText(),
	            TargetSeed.getText(),
	            clearMode,
	            captureEntities,
	            captureItems
	        ));
	        Keyboard.enableRepeatEvents(false);
	    }
	    @Override public boolean doesGuiPauseGame() { return false; }
	}
	public static class GUIButtonPressedMessageHandler implements IMessageHandler<GUIButtonPressedMessage, IMessage> {
		@Override
		public IMessage onMessage(GUIButtonPressedMessage message, MessageContext context) {
			EntityPlayerMP entity = context.getServerHandler().player;
			entity.getServerWorld().addScheduledTask(() -> {
				int buttonID = message.buttonID;
				int x = message.x;
				int y = message.y;
				int z = message.z;
				handleButtonAction(entity, buttonID, x, y, z);
			});
			return null;
		}
	}
	public static class GUIButtonPressedMessage implements IMessage {
		int buttonID, x, y, z;
		public GUIButtonPressedMessage() {}
		public GUIButtonPressedMessage(int buttonID, int x, int y, int z) {
			this.buttonID = buttonID;
			this.x = x; this.y = y; this.z = z;
		}
		@Override public void toBytes(io.netty.buffer.ByteBuf buf) {
			buf.writeInt(buttonID);
			buf.writeInt(x);
			buf.writeInt(y);
			buf.writeInt(z);
		}
		@Override public void fromBytes(io.netty.buffer.ByteBuf buf) {
			buttonID = buf.readInt();
			x = buf.readInt();
			y = buf.readInt();
			z = buf.readInt();
		}
	}
	private static void handleButtonAction(EntityPlayer entity, int buttonID, int x, int y, int z) {
		World world = entity.world;
		if (!world.isBlockLoaded(new BlockPos(x, y, z))) return;
	}
}
