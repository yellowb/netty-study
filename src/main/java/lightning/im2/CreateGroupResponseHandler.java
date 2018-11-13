package lightning.im2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lightning.im.CreateGroupResponsePacket;

import java.util.*;

import static lightning.im.CreateGroupResponsePacket.*;

public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket responsePacket) throws Exception {
        if (responsePacket.getResponseCode() == CREATE_GROUP_SUCCEED) {
            System.out.println(new Date() + ": 群组创建成功! 群名 = " + responsePacket.getGroupName() + ", 创建者 = " + responsePacket.getCreator() + ", "
                + "成员 = " + responsePacket.getMembers());
        } else {
            System.out.println(new Date() + ": 群组创建失败! 群名 = " + responsePacket.getGroupName() + ", 创建者 = " + responsePacket.getCreator());
        }
    }
}
