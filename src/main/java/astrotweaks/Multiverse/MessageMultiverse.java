package astrotweaks.Multiverse;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Server &rarr; client: tells the client which dimension ids belong to a multiverse
 * level (or the shared global dimension) so it can register the DimensionTypes
 * BEFORE the respawn packet arrives.
 */
public class MessageMultiverse implements IMessage {

    private static final int GLOBAL_SENTINEL = -1;

    private int baseDimId;
    private boolean global;

    public MessageMultiverse() {
    }

    public MessageMultiverse(int baseDimId) {
        this.baseDimId = baseDimId;
        this.global = false;
    }

    public static MessageMultiverse forGlobal() {
        MessageMultiverse message = new MessageMultiverse(GLOBAL_SENTINEL);
        message.global = true;
        return message;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.baseDimId = buf.readInt();
        this.global = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.baseDimId);
        buf.writeBoolean(this.global);
    }

    /**
     * Runs on the client netty thread. Must stay synchronous: the registration has to
     * be visible before the (already queued) respawn packet creates the WorldClient.
     */
    public static class ClientHandler implements IMessageHandler<MessageMultiverse, IMessage> {

        @Override
        public IMessage onMessage(MessageMultiverse message, MessageContext ctx) {
            if (ctx.side == Side.CLIENT) {
                if (message.global) {
                    MultiverseDims.registerGlobalDimension();
                } else {
                    MultiverseDims.registerLevelDimensions(message.baseDimId);
                }
            }
            return null;
        }
    }
}