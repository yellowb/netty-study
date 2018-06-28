package ch3_core_functions.channelhandler_and_channelpipeline;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class HelloClient {

    private void start(String host, int port) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new HelloClientInBoundHandler());
                }
            }).option(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.connect(host, port).sync();

            System.out.println(HelloClient.class.getSimpleName() + ", HelloClient started.");

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully().sync();
            System.out.println(HelloClient.class.getSimpleName() + ", HelloClient shutdown.");
        }
    }

    public static void main(String[] args) throws Exception {
        new HelloClient().start("localhost", 10086);
    }

}
