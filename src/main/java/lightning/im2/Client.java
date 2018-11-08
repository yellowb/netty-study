package lightning.im2;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.AttributeKey;
import lightning.im.MessageRequestPacket;
import lightning.im.Packet;
import lightning.im.PacketCodeC;

import java.util.*;
import java.util.concurrent.*;

public class Client {

    private static final int MAX_RETRY = 5;

    private static final PacketCodeC CODEC = new PacketCodeC();

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
//                    ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));
                    ch.pipeline().addLast(new Spliter());
                    ch.pipeline().addLast(new PacketCodec());
                    ch.pipeline().addLast(new LoginResponseHandler());
                    ch.pipeline().addLast(new MessageResponseHandler());
                    ch.pipeline().addLast(new ClientStatusDetectHandler());
                }
            });

        // 4.建立连接
        connect(bootstrap, "localhost", 10086, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 连接成功!");

                // 启动新线程接收命令行输入
                final Channel channel = ((ChannelFuture)future).channel();
                startConsoleThread(channel);
//                startFastThread(channel);
            } else if (retry == 0) {
                System.err.println(new Date() + ": 重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第 " + order + " 次重连……");
                bootstrap.config().group().schedule(new Runnable() {
                    @Override
                    public void run() {
                        connect(bootstrap, host, port, retry - 1);
                    }
                }, delay, TimeUnit.SECONDS);
            }
        });
    }

    private static void startConsoleThread(final Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                System.out.println("输入信息 : ");
                Scanner sc = new Scanner(System.in);
                String line = sc.nextLine();

//                MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
//                messageRequestPacket.setMessage(line);
//                //                ByteBuf byteBuf = CODEC.encode(messageRequestPacket);
//                //                channel.writeAndFlush(byteBuf);

                Packet packet = StringToPacketUtil.toPacket(line);
                channel.writeAndFlush(packet);
            }
        }).start();
    }



    private static void startFastThread(final Channel channel) {
        new Thread(() -> {
            String line = "Hello, my name is Lily, how are you?";

            MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
            messageRequestPacket.setMessage(line);
            for (int i = 0; i < 1; i++) {
                channel.writeAndFlush(messageRequestPacket);
            }

            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            channel.close();
            System.out.println(new Date() + ": 断开连接");

        }).start();
    }

}
