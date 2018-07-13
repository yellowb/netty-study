package kcp_simple_demo;

import io.jpower.kcp.netty.ChannelOptionHelper;
import io.jpower.kcp.netty.UkcpChannel;
import io.jpower.kcp.netty.UkcpChannelOption;
import io.jpower.kcp.netty.UkcpClientChannel;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Sends one message when a connection is open and echoes back any received
 * data to the server.
 *
 * @author <a href="mailto:szhnet@gmail.com">szh</a>
 */
public final class EchoClient {

    static final int CONV = Integer.parseInt(System.getProperty("conv", "11"));
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8008"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "2"));

    public static void main(String[] args) throws Exception {
        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(UkcpClientChannel.class)
//                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<UkcpChannel>() {
                @Override
                public void initChannel(UkcpChannel ch) throws Exception {
                    ChannelPipeline p = ch.pipeline();
                    p.addLast(new EchoClientHandler());
                }
            });
            ChannelOptionHelper.nodelay(b, true, 40, 2, true)
                .option(UkcpChannelOption.UKCP_MTU, 512)
//                .option(UkcpChannelOption.UKCP_AUTO_SET_CONV, true)
//                .option(UkcpChannelOption.UKCP_STREAM, true)
                .option(UkcpChannelOption.UKCP_RCV_WND, 128)
                .option(UkcpChannelOption.UKCP_SND_WND, 128);

            // Start the client.
            ChannelFuture f = b.connect(HOST, PORT).sync();

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
    }

}
