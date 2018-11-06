package lightning.im2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lightning.im.MessageRequestPacket;
import lightning.im.MessageResponsePacket;

import java.util.*;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        System.out.println(new Date() + ": 客户端发来消息: " + messageRequestPacket.getMessage());
        String resp = "ECHO: " + messageRequestPacket.getMessage();
        messageResponsePacket.setMessage(resp);
        ctx.channel().write(messageResponsePacket);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().flush();
        super.channelReadComplete(ctx);
    }
}
