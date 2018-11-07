package lightning.im2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.*;

public class ClientStatusDetectHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端建立连接: " + ctx.channel().remoteAddress().toString());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端断开连接: " + ctx.channel().remoteAddress().toString());
        super.channelInactive(ctx);
    }
}
