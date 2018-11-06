package lightning.im2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lightning.im.PacketCodeC;

import java.util.*;

public class Spliter extends LengthFieldBasedFrameDecoder {

    private static final int LENGTH_FIELD_OFFSET = 7;
    private static final int LENGTH_FIELD_LENGTH = 4;

    public Spliter() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {

        // protocol checking
        int magicNumber = in.getInt(in.readerIndex());
        if (magicNumber != PacketCodeC.MAGIC_NUMBER) {
            System.out.println(new Date() + ": 客户端发来不明协议[" + magicNumber + "]");
            ctx.channel().close().sync();
            return null;
        }

        return super.decode(ctx, in);
    }
}
