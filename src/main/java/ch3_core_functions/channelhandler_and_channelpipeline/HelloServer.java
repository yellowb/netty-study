package ch3_core_functions.channelhandler_and_channelpipeline;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HelloServer {

    private void start(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new HelloServerOutBoundHandler1());
                    ch.pipeline().addLast(new HelloServerOutBoundHandler2());
                    ch.pipeline().addLast(new HelloServerInBoundHandler1());
                    ch.pipeline().addLast(new HelloServerInBoundHandler2());
                }
            }).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();

            System.out.println(HelloClient.class.getSimpleName() + ", HelloServer started. listening on: " + f.channel().localAddress());

            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully().sync();
            workerGroup.shutdownGracefully().sync();
            System.out.println(HelloClient.class.getSimpleName() + ", HelloServer shutdown. ");
        }
    }

    public static void main(String[] args) throws Exception {
        new HelloServer().start(10086);
    }
}
