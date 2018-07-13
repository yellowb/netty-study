package kcp_simple_demo;

import io.jpower.kcp.netty.UkcpChannel;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handler implementation for the echo client.
 *
 * @author <a href="mailto:szhnet@gmail.com">szh</a>
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private final static Logger LOG = LoggerFactory.getLogger(EchoClientHandler.class);

    private final ByteBuf firstMessage;

    /**
     * Creates a client-side handler.
     */
    public EchoClientHandler() {
        firstMessage = Unpooled.buffer(EchoClient.SIZE);
        for (int i = 0; i < firstMessage.capacity(); i++) {
            firstMessage.writeByte(i % 26 + 97);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        UkcpChannel kcpCh = (UkcpChannel) ctx.channel();
        kcpCh.conv(EchoClient.CONV); // set conv

        for(int i = 0; i < 15; i++) {
            ByteBuf slice = firstMessage.retainedSlice();
            LOG.info("Client send: {}", slice.toString(CharsetUtil.UTF_8));
            ctx.writeAndFlush(slice);
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("干干干");
            e.printStackTrace();
        }

        LOG.info("Client send: {}", firstMessage.toString(CharsetUtil.UTF_8));
        ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ctx.write(msg);
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
//        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

}
