package lightning.im2;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lightning.im.ChatGroup;
import lightning.im.CreateGroupRequestPacket;
import lightning.im.CreateGroupResponsePacket;
import lightning.im.GroupChatUtil;
import lightning.im.LoginUtil;

import java.util.*;

import static lightning.im.CreateGroupResponsePacket.*;

@ChannelHandler.Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket requestPacket) throws Exception {

        CreateGroupResponsePacket responsePacket = new CreateGroupResponsePacket();

        String groupName = requestPacket.getGroupName();
        List<String> members = requestPacket.getMembers();
        // Add creator
        members.add(LoginUtil.getCurrentUserSession(ctx.channel()).getUsername());
        requestPacket.setCreator(LoginUtil.getCurrentUserSession(ctx.channel()).getUsername());
        String creator = requestPacket.getCreator();

        System.out.println(new Date() + ": 收到创建群组请求. 群名 = " + requestPacket.getGroupName() + ", 创建者 = " + requestPacket.getCreator() + ", "
            + "成员 = " + requestPacket.getMembers());

        ChatGroup chatGroup = GroupChatUtil.createGroup(groupName, members);
        responsePacket.setResponseCode(chatGroup != null ? CREATE_GROUP_SUCCEED : CREATE_GROUP_FAILED);
        responsePacket.setGroupName(groupName);
        responsePacket.setCreator(creator);
        responsePacket.setMembers(chatGroup.getMembers());

        chatGroup.getChannelGroup().writeAndFlush(responsePacket);
    }
}
