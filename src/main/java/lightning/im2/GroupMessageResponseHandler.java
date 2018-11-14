package lightning.im2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lightning.im.GroupMessageResponsePacket;

import java.util.*;

import static lightning.im.GroupMessageResponsePacket.*;

public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {
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
