package ch3_core_functions.channelhandler_and_channelpipeline;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class HelloClientInBoundHandler extends ChannelInboundHandlerAdapter {

    private static final String CLASSNAME = HelloClientInBoundHandler.class.getSimpleName();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(CLASSNAME + "," + "channelActive");
        String name = "Ken";
        ByteBuf message = Unpooled.copiedBuffer(name, CharsetUtil.UTF_8);
        ctx.write(message);
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf message = (ByteBuf) msg;
        String messageString = message.toString(CharsetUtil.UTF_8);

        message.release();  // free the pooled buf

        System.out.println(CLASSNAME + "," + "channelRead" + "," + messageString);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println(CLASSNAME + "," + "channelReadComplete");
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }
}
