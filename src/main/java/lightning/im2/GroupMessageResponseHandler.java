package lightning.im2;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lightning.im.GroupMessageResponsePacket;

import java.util.*;

import static lightning.im.GroupMessageResponsePacket.*;

@ChannelHandler.Sharable
public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {

    public static final GroupMessageResponseHandler INSTANCE = new GroupMessageResponseHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket responsePacket) throws Exception {
        if (GROUP_MSG_SENT_SUCCEED == responsePacket.getResponseCode()) {
            String sender = responsePacket.getSender();
            String groupName = responsePacket.getGroupName();
            String message = responsePacket.getMessage();
            System.out.println(new Date() + ": " + sender + " 对[" + groupName + "]里的小伙伴说: " + message);
        }
        else {
//            String groupName = responsePacket.getGroupName();
            String error = responsePacket.getError();
            System.out.println(new Date() + ": " + error);
        }
    }
}
