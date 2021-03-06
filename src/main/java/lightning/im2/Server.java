package lightning.im2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.*;

public class Server {

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
            .group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                protected void initChannel(NioSocketChannel ch) {
                    ch.pipeline().addLast(new ClientIdleStateHandler());
                    ch.pipeline().addLast(new Spliter());
//                    ch.pipeline().addLast(new PacketCodec());
                    ch.pipeline().addLast(PacketCodec2.INSTANCE);
                    ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                    ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);
                    ch.pipeline().addLast(AuthHandler.INSTANCE);
                    ch.pipeline().addLast(MessageRequestHandler.INSTANCE);
                    ch.pipeline().addLast(CreateGroupRequestHandler.INSTANCE);
                    ch.pipeline().addLast(GroupMessageRequestHandler.INSTANCE);
                    ch.pipeline().addLast(ClientStatusDetectHandler.INSTANCE);
                }
            })
            .childOption(ChannelOption.TCP_NODELAY, true)
            .option(ChannelOption.SO_BACKLOG, 1024);

        serverBootstrap.bind(10086).addListener(new GenericFutureListener<Future<? super Void>>() {
            public void operationComplete(Future<? super Void> future) {
                if (future.isSuccess()) {
                    System.out.println(new Date() + ": 端口绑定成功!");
                } else {
                    System.err.println(new Date() + ": 端口绑定失败!");
                }
            }
        });
    }

}
