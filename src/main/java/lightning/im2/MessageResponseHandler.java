package lightning.im2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lightning.im.MessageResponsePacket;

import java.util.*;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageResponsePacket) throws Exception {
        System.out.println(new Date() + ": [" + messageResponsePacket.getFromUsername() + "]" + messageResponsePacket.getMessage());
    }
}
