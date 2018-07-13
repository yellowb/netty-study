package kcp_simple_demo;

import io.jpower.kcp.netty.ChannelOptionHelper;
import io.jpower.kcp.netty.UkcpChannel;
import io.jpower.kcp.netty.UkcpChannelOption;
import io.jpower.kcp.netty.UkcpServerChannel;
import io.netty.bootstrap.UkcpServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Echoes back any received data from a client.
 *
 * @author <a href="mailto:szhnet@gmail.com">szh</a>
 */
public final class EchoServer {

    static final int CONV = Integer.parseInt(System.getProperty("conv", "11"));
    static final int PORT = Integer.parseInt(System.getProperty("port", "8008"));

    public static void main(String[] args) throws Exception {
        // Configure the server.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            UkcpServerBootstrap b = new UkcpServerBootstrap();
            b.group(group)
                .channel(UkcpServerChannel.class)
//                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<UkcpChannel>() {
                    @Override
                    public void initChannel(UkcpChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new EchoServerHandler());
                    }
                });
            ChannelOptionHelper.nodelay(b, true, 40, 2, true)
                .childOption(UkcpChannelOption.UKCP_MTU, 512)
//                .childOption(UkcpChannelOption.UKCP_AUTO_SET_CONV, true)
//                .childOption(UkcpChannelOption.UKCP_STREAM, true)
                .childOption(UkcpChannelOption.UKCP_RCV_WND, 128)
                .childOption(UkcpChannelOption.UKCP_SND_WND, 128);

            // Start the server.
            ChannelFuture f = b.bind(PORT).sync();

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } finally {
            // Shut down all event loops to terminate all threads.
            group.shutdownGracefully();
        }
    }

}
