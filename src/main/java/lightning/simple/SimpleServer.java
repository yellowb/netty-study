package lightning.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.nio.charset.*;
import java.util.*;

public class SimpleServer {
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
            .group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                protected void initChannel(NioSocketChannel ch) {
                    ch.pipeline().addLast(new FirstServerHandler());
                }
            })
            .childOption(ChannelOption.TCP_NODELAY, true)
            .option(ChannelOption.SO_BACKLOG, 1024);

        serverBootstrap.bind(10086).addListener(new GenericFutureListener<Future<? super Void>>() {
            public void operationComplete(Future<? super Void> future) {
                if (future.isSuccess()) {
                    System.out.println("端口绑定成功!");
                } else {
                    System.err.println("端口绑定失败!");
                }
            }
        });
    }

    static class FirstServerHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

            String inMsg = ((ByteBuf)msg).toString(Charset.forName("utf-8"));

            System.out.println(new Date() + " - 接收到客户端消息:" + inMsg);

            if ("How are you?".equals(inMsg)) {
                String outMsg = "Fine. Thank you and you?";
                ByteBuf outMsgBuffer = ctx.alloc().buffer();

                System.out.println(outMsgBuffer.capacity());
                System.out.println(outMsgBuffer.maxCapacity());

                outMsgBuffer.writeBytes(outMsg.getBytes(Charset.forName("utf-8")));
                ctx.channel().writeAndFlush(outMsgBuffer);
                System.out.println(new Date() + " - 向客户端发送消息:" + outMsg);
            }

        }
    }
}
