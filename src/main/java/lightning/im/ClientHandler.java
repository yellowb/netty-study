package lightning.im;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.*;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    private static final PacketCodeC CODEC = new PacketCodeC();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端开始登录");

        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(10086);
        loginRequestPacket.setUsername("yellow");
        loginRequestPacket.setPassword("12345");

        ByteBuf byteBuf = CODEC.encode(loginRequestPacket);

        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf inByteBuf = (ByteBuf)msg;

        Packet serverRespPacket = CODEC.decode(inByteBuf);

        // Process different packet
        if (serverRespPacket instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) serverRespPacket;
            if (loginResponsePacket.getLoginResponse() == LoginResponsePacket.LOGIN_PASSED) {
                LoginUtil.markAsLogin(ctx.channel());
                System.out.println(new Date() + ": 客户端登录成功 ");
            }
            else {
                LoginUtil.markAsLogout(ctx.channel());
                System.out.println(new Date() + ": 客户端登录失败[" + loginResponsePacket.getErrMsg() + "]");
            }
        } else if (serverRespPacket instanceof MessageResponsePacket) {
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) serverRespPacket;
            System.out.println(new Date() + ": 服务器回应: " + messageResponsePacket.getMessage());
        }

    }
}
