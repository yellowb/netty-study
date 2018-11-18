package lightning.im2;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lightning.im.LoginRequestPacket;
import lightning.im.LoginResponsePacket;
import lightning.im.LoginUtil;

import java.util.*;

@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    private static final Map<String, String> VALID_USERS = new HashMap<>();

    static {
        VALID_USERS.put("yellow", "123");
        VALID_USERS.put("black", "123");
        VALID_USERS.put("red", "123");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        String username = loginRequestPacket.getUsername();
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setUsername(username);
        if (this.validUser(loginRequestPacket)) {
            // Login passed

            LoginUtil.login(new Session(loginRequestPacket.getUsername()), ctx.channel());

            System.out.println(new Date() + ": 登录验证成功! - " + loginRequestPacket.getUsername());
            loginResponsePacket.setLoginResponse(LoginResponsePacket.LOGIN_PASSED);
        }
        else {
            // Login denied

            LoginUtil.logout(ctx.channel());

            System.out.println(new Date() + ": 登录验证失败! - " + loginRequestPacket.getUsername());
            loginResponsePacket.setLoginResponse(LoginResponsePacket.LOGIN_DENIED);
            loginResponsePacket.setErrMsg("Username/PWD error");
        }
        ctx.channel().write(loginResponsePacket);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().flush();
        super.channelReadComplete(ctx);
    }

    private boolean validUser(LoginRequestPacket loginRequestPacket) {
        String pwd = VALID_USERS.get(loginRequestPacket.getUsername());
        return loginRequestPacket.getPassword().equals(pwd);
    }

}
