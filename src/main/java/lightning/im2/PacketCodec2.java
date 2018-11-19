package lightning.im2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lightning.im.Packet;
import lightning.im.PacketCodeC;

import java.util.List;

@ChannelHandler.Sharable
public class PacketCodec2 extends MessageToMessageCodec<ByteBuf, Packet> {

    public static final PacketCodec2 INSTANCE = new PacketCodec2();

    private static final PacketCodeC CODEC = new PacketCodeC();

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, List<Object> out) throws Exception {
        ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();
        CODEC.encode(byteBuf, msg);
        out.add(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        out.add(CODEC.decode(msg));
    }
}
