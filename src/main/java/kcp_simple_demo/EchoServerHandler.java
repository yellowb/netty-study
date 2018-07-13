package kcp_simple_demo;

import io.jpower.kcp.netty.UkcpChannel;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handler implementation for the echo server.
 *
 * @author <a href="mailto:szhnet@gmail.com">szh</a>
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        UkcpChannel kcpCh = (UkcpChannel)ctx.channel();
        kcpCh.conv(EchoServer.CONV);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf)msg;

        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread() + "|Server do something: " + buf.toString(CharsetUtil.UTF_8));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println("干干干");
                    e.printStackTrace();
                }
            }
        });

        System.out.println(Thread.currentThread() + "|Server recv: " + buf.toString(CharsetUtil.UTF_8));
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

}
