package lightning.im2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lightning.im.LoginRequestPacket;
import lightning.im.LoginResponsePacket;

import java.util.*;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    private static final Map<String, String> VALID_USERS = new HashMap<>();

    static {
        VALID_USERS.put("yellow", "12345");
        VALID_USERS.put("black", "54321");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        String username = loginRequestPacket.getUsername();
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setUsername(username);
        if (this.validUser(loginRequestPacket)) {
            // Login passed
            System.out.println(new Date() + ": 登录验证成功! - " + loginRequestPacket.getUsername());
            loginResponsePacket.setLoginResponse(LoginResponsePacket.LOGIN_PASSED);
        }
        else {
            // Login denied
            System.out.println(new Date() + ": 登录验证失败! - " + loginRequestPacket.getUsername());
            loginResponsePacket.setLoginResponse(LoginResponsePacket.LOGIN_DENIED);
            loginResponsePacket.setErrMsg("Username/PWD error");
        }
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean validUser(LoginRequestPacket loginRequestPacket) {
        String pwd = VALID_USERS.get(loginRequestPacket.getUsername());
        return loginRequestPacket.getPassword().equals(pwd);
    }

}
