package lightning.im;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.*;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private static final PacketCodeC CODEC = new PacketCodeC();

    private static final Map<String, String> VALID_USERS = new HashMap<>();

    static {
        VALID_USERS.put("yellow", "12345");
        VALID_USERS.put("black", "54321");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf)msg;

        Packet packet = CODEC.decode(byteBuf);

        // Process different packet
        if (packet instanceof LoginRequestPacket) {
            // Login
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket)packet;
            LoginResponsePacket loginResponsePacket = null;

            if (validUser(loginRequestPacket)) {
                // Login passed
                System.out.println(new Date() + ": 登录验证成功! - " + loginRequestPacket.getUsername());
                loginResponsePacket = this.buildLoginResponse(true, loginRequestPacket.getUsername(), null);
            } else {
                // Login denied
                System.out.println(new Date() + ": 登录验证失败! - " + loginRequestPacket.getUsername());
                loginResponsePacket = this.buildLoginResponse(false, loginRequestPacket.getUsername(), "Username/PWD error");
            }

            ByteBuf respByteBuf = CODEC.encode(loginResponsePacket);
            ctx.channel().writeAndFlush(respByteBuf);
        } else if (packet instanceof MessageRequestPacket) {
            // Msg from client
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket)packet;
            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            System.out.println(new Date() + ": 客户端发来消息: " + messageRequestPacket.getMessage());
            String resp = "ECHO: " + messageRequestPacket.getMessage();
            messageResponsePacket.setMessage(resp);
            ByteBuf respByteBuf = CODEC.encode(messageResponsePacket);
            ctx.channel().writeAndFlush(respByteBuf);
        }

    }

    private boolean validUser(LoginRequestPacket loginRequestPacket) {
        String pwd = VALID_USERS.get(loginRequestPacket.getUsername());
        return loginRequestPacket.getPassword().equals(pwd);
    }

    private LoginResponsePacket buildLoginResponse(boolean passed, String username, String errMsg) {
        LoginResponsePacket packet = new LoginResponsePacket();
        packet.setLoginResponse(passed ? LoginResponsePacket.LOGIN_PASSED : LoginResponsePacket.LOGIN_DENIED);
        packet.setUsername(username);
        packet.setErrMsg(errMsg);
        return packet;
    }
}
