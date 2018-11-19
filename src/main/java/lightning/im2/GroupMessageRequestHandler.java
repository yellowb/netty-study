package lightning.im2;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lightning.im.ChatGroup;
import lightning.im.GroupChatUtil;
import lightning.im.GroupMessageRequestPacket;
import lightning.im.GroupMessageResponsePacket;
import lightning.im.LoginUtil;

import java.util.*;

import static lightning.im.GroupMessageResponsePacket.*;

@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {

    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket requestPacket) throws Exception {
        String sender = LoginUtil.getCurrentUserSession(ctx.channel()).getUsername();
        String groupName = requestPacket.getGroupName();
        String message = requestPacket.getMessage();

        System.out.println(new Date() + ": 服务器收到群聊消息: [" + groupName + "]" + message + " @ " + sender);

        ChatGroup chatGroup = GroupChatUtil.getGroup(groupName);
        if (chatGroup != null) {
            GroupMessageResponsePacket responsePacket = new GroupMessageResponsePacket();
            responsePacket.setResponseCode(GROUP_MSG_SENT_SUCCEED);
            responsePacket.setGroupName(groupName);
            responsePacket.setMessage(message);
            responsePacket.setSender(sender);
            chatGroup.getChannelGroup().writeAndFlush(responsePacket);
        }
        else {
            GroupMessageResponsePacket responsePacket = new GroupMessageResponsePacket();
            responsePacket.setResponseCode(GROUP_MSG_SENT_FAILED);
            responsePacket.setGroupName(groupName);
            responsePacket.setSender(sender);
            responsePacket.setError("群组[" + groupName + "]不存在.");
            ctx.writeAndFlush(responsePacket);
        }
    }
}
