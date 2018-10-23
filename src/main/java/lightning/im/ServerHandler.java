package lightning.im;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private static final PacketCodeC CODEC = new PacketCodeC();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf)msg;

        Packet packet = CODEC.decode(byteBuf);

        if (packet instanceof LoginRequestPacket) {
            // Login
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket)packet;
            if (validUser(loginRequestPacket)) {
                // Login passed
            } else {
                // Login denied
            }
        }

    }

    private boolean validUser(LoginRequestPacket loginRequestPacket) {
        return (loginRequestPacket.getUsername().equals("yellow") && loginRequestPacket.getPassword().equals("12345")) || (
            loginRequestPacket.getUsername().equals("black") && loginRequestPacket.getPassword().equals("54321"))；
    }
}
