package len_field_based_and_protobuf_demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.compression.SnappyFrameDecoder;
import io.netty.handler.codec.compression.SnappyFrameEncoder;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import len_field_based_and_protobuf_demo.beans.ExpressionProto;
import len_field_based_and_protobuf_demo.beans.ResultProto;

import java.util.*;

public class DemoClient {


    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        // Outbound
                        pipeline.addLast(new SnappyFrameEncoder());
                        pipeline.addLast(new LengthFieldPrepender(4));
                        pipeline.addLast(new ProtobufEncoder());
                        // Inbound
                        pipeline.addLast(new SnappyFrameDecoder());
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4));
                        pipeline.addLast(new ProtobufDecoder(ResultProto.Result.getDefaultInstance()));
                        // Tail biz handler
                        pipeline.addLast("bizHandler", new ClientBizHandler());
                    }
                }).option(ChannelOption.TCP_NODELAY, true);

            ChannelFuture channelFuture = bootstrap.connect("localhost", 10086).sync();

//            while(1 == 1) {
//                System.out.println("Enter your expression: ");
//                Scanner scanner = new Scanner(System.in);
//                String strExp = scanner.nextLine();
//
//                ExpressionProto.Expression exp = ExpressionUtil.fromString(strExp);
//                channelFuture.channel().writeAndFlush(exp);
//            }

            for(;;) {
                ExpressionProto.Expression exp = ExpressionUtil.fromString("1+2");
                channelFuture.channel().writeAndFlush(exp);
                Thread.sleep(100);
            }


//            channelFuture.channel().closeFuture().sync();
        }
        finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
