package ch3_core_functions.channelhandler_and_channelpipeline;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.CharsetUtil;

public class HelloServerOutBoundHandler1 extends ChannelOutboundHandlerAdapter {

    private static final String CLASSNAME = HelloServerOutBoundHandler1.class.getSimpleName();

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ByteBuf message = (ByteBuf)msg;
        String messageString = message.toString(CharsetUtil.UTF_8);

        System.out.println(CLASSNAME + "," + "write" + "," + messageString + "," + msg);

        String replyString = messageString + ", and me!";
        ByteBuf reply = Unpooled.copiedBuffer(replyString, CharsetUtil.UTF_8);

        ctx.write(reply, promise);
        ctx.flush();
    }
}
