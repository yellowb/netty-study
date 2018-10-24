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

        if (packet instanceof LoginRequestPacket) {
            // Login
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket)packet;
            if (validUser(loginRequestPacket)) {
                // Login passed
                System.out.println(new Date() + ": 登录验证成功! - " + loginRequestPacket.getUsername());
                //TODO Add response
            } else {
                // Login denied
                System.out.println(new Date() + ": 登录验证失败! - " + loginRequestPacket.getUsername());
                //TODO Add response
            }
        }

    }

    private boolean validUser(LoginRequestPacket loginRequestPacket) {
        String pwd = VALID_USERS.get(loginRequestPacket.getUsername());
        return loginRequestPacket.getPassword().equals(pwd);
    }
}
