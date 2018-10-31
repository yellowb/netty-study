package lightning.im2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lightning.im.Packet;
import lightning.im.PacketCodeC;

import java.util.*;

public class PacketCodec extends ByteToMessageCodec<Packet> {

    private static final PacketCodeC CODEC = new PacketCodeC();

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        CODEC.encode(out, msg);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(CODEC.decode(in));
    }
}
