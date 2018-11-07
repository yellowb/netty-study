package lightning.im2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lightning.im.LoginUtil;

import java.util.*;

/**
 * 判断Login是否成功
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (LoginUtil.hasLogin(ctx.channel())) {
            // 当前连接已登录, 卸载AuthHandler
            ctx.pipeline().remove(this);
            System.out.println(new Date() + ": 登录验证成功后卸载AuthHandler!");
            super.channelRead(ctx, msg);
        }
        else {
            ctx.channel().close();
            System.out.println(new Date() + ": 登录验证失败后断开与客户端连接!");
        }
    }
}
