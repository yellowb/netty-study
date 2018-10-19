package lightning.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.nio.charset.*;
import java.util.*;
import java.util.concurrent.*;

public class SimpleClient {
    private static final int MAX_RETRY = 5;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
            // 1.指定线程模型
            .group(workerGroup)
            // 2.指定 IO 类型为 NIO
            .channel(NioSocketChannel.class)
            // 绑定自定义属性到 channel
            .attr(AttributeKey.newInstance("clientName"), "nettyClient")
            // 设置TCP底层属性
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .option(ChannelOption.TCP_NODELAY, true)
            // 3.IO 处理逻辑
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(new FirstClientHandler());
                }
            });

        // 4.建立连接
        connect(bootstrap, "localhost", 10086, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(new Runnable() {
                    @Override
                    public void run() {
                        connect(bootstrap, host, port, retry - 1);
                    }
                }, delay, TimeUnit.SECONDS);
            }
        });
    }

    static class FirstClientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("Channel 已激活");

            ByteBuf buffer = ctx.alloc().buffer();
            buffer.writeBytes("How are you?".getBytes(Charset.forName("utf-8")));

            ctx.channel().writeAndFlush(buffer);

            System.out.println(new Date() + " - 向服务器发送消息:" + "How are you?");

        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            String inMsg = ((ByteBuf)msg).toString(Charset.forName("utf-8"));

            System.out.println(new Date() + " - 接收到服务器消息:" + inMsg);
        }
    }
}
