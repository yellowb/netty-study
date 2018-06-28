package ch3_core_functions.channelhandler_and_channelpipeline;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class HelloServerInBoundHandler2 extends ChannelInboundHandlerAdapter {

    private static final String CLASSNAME = HelloServerInBoundHandler2.class.getSimpleName();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf message = (ByteBuf)msg;
        String name = message.toString(CharsetUtil.UTF_8);
        System.out.println(CLASSNAME + "," + "channelRead" + "," + "name = " + name + "," + msg);

        String replyString = "Hello, " + name;
        ByteBuf reply = Unpooled.copiedBuffer(replyString, CharsetUtil.UTF_8);
        message.release();  // free the pooled buf
        ctx.write(reply);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println(CLASSNAME + "," + "channelReadComplete");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }
}
