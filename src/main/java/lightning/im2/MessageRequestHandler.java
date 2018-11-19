package lightning.im2;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lightning.im.LoginUtil;
import lightning.im.MessageRequestPacket;
import lightning.im.MessageResponsePacket;

import java.util.*;

@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {

        System.out.println(new Date() + ": 客户端发来消息: " + messageRequestPacket.getMessage());
        //        String resp = "ECHO: " + messageRequestPacket.getMessage();
        //        messageResponsePacket.setMessage(resp);
        //        ctx.channel().write(messageResponsePacket);

        String[] tokens = messageRequestPacket.getMessage().split(" ", 2);
        String toUsername = tokens[0];
        String message = tokens[1];

        if (LoginUtil.hasLogin(toUsername)) {
            MessageResponsePacket toUserPacket = new MessageResponsePacket();
            toUserPacket.setMessage(message);
            toUserPacket.setFromUsername(LoginUtil.getCurrentUserSession(ctx.channel()).getUsername());
            LoginUtil.getLoginUserChannel(toUsername).writeAndFlush(toUserPacket);
        } else {
            MessageResponsePacket echoPacket = new MessageResponsePacket();
            echoPacket.setMessage("用户[" + toUsername + "] 未登录!");
            echoPacket.setFromUsername("SYSTEM INFO");
            ctx.channel().write(echoPacket);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().flush();
        super.channelReadComplete(ctx);
    }
}
