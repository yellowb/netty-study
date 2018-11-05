package lightning.im2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lightning.im.LoginResponsePacket;
import lightning.im.LoginUtil;

import java.util.*;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        if (LoginResponsePacket.LOGIN_PASSED == loginResponsePacket.getLoginResponse()) {
            LoginUtil.markAsLogin(ctx.channel());
            System.out.println(new Date() + ": 客户端登录成功 ");
        }
        else {
            LoginUtil.markAsLogout(ctx.channel());
            System.out.println(new Date() + ": 客户端登录失败[" + loginResponsePacket.getErrMsg() + "]");
        }
    }
}
