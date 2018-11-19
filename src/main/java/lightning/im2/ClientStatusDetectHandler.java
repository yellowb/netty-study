package lightning.im2;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lightning.im.LoginUtil;

import java.util.*;

@ChannelHandler.Sharable
public class ClientStatusDetectHandler extends ChannelInboundHandlerAdapter {

    public static final ClientStatusDetectHandler INSTANCE = new ClientStatusDetectHandler();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端建立连接: " + ctx.channel().remoteAddress().toString());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端断开连接, 清除其登录信息: " + ctx.channel().remoteAddress().toString());
        LoginUtil.logout(ctx.channel());
        super.channelInactive(ctx);
    }
}
