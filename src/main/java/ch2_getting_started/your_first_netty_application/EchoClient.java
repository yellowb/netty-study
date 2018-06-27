package ch2_getting_started.your_first_netty_application;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.*;

public class EchoClient {

    private String host;

    private int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap boot = new Bootstrap();

            boot.group(group).channel(NioSocketChannel.class).remoteAddress(new InetSocketAddress(this.host, this.port))
                .handler(new EchoClientChannelInitializer());

            ChannelFuture future = boot.connect().sync();

            System.out.println("EchoClient connected to server. ");

            future.channel().closeFuture().sync();  // If server close Channel, will go into here.
        } catch (Exception e) {
            throw e;
        } finally {
            group.shutdownGracefully().sync();
            System.out.println("EchoClient shutdown.");
        }
    }

    static class EchoClientChannelInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new EchoClientHandler());
        }
    }

    public static void main(String[] args) throws Exception {
        new EchoClient("localhost", 10086).start();
    }

}
