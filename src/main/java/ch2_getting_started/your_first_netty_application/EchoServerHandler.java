package ch2_getting_started.your_first_netty_application;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(Thread.currentThread() + "Server : Channel active.");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(Thread.currentThread() + "Server : Channel inactive.");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf)msg;
        String data = in.toString(CharsetUtil.UTF_8);
        System.out.println(Thread.currentThread() + "Server received: " + data.length() + " Bytes , " + data);

        Thread.sleep(3000);
        //        ctx.writeAndFlush(in);  // If use this, client will receive the msg immediately.
        ctx.write(in);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 如果读完客户端发过来的消息, 就关闭这个Channel, 会引起客户端关闭.
        System.out.println(Thread.currentThread() + "Server close the channel. ");
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("EchoServer exception.");
        cause.printStackTrace();
        ctx.close();
    }
}
