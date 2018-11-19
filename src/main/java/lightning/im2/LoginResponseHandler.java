package lightning.im2;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lightning.im.LoginResponsePacket;
import lightning.im.LoginUtil;

import java.util.*;

@ChannelHandler.Sharable
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    public static final LoginResponseHandler INSTANCE = new LoginResponseHandler();

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
