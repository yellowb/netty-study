package ch2_getting_started.your_first_netty_application;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.*;

public class EchoServer {

    private int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            ServerBootstrap boot = new ServerBootstrap();
            boot.group(group).channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(this.port))
                .childHandler(new EchoServerChannelInitializer());

            ChannelFuture future = boot.bind().sync();
            System.out.println(EchoServer.class.getName() + " started and listen on " + future.channel().localAddress());
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            throw e;
        } finally {
            group.shutdownGracefully().sync();
            System.out.println(EchoServer.class.getName() + " shutdown.");
        }

    }

    static class EchoServerChannelInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new EchoServerHandler());
        }
    }

    public static void main(String[] args) throws Exception {
        new EchoServer(10086).start();
    }
}
