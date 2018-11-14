package lightning.im2;

import lightning.im.CreateGroupRequestPacket;
import lightning.im.GroupMessageRequestPacket;
import lightning.im.LoginRequestPacket;
import lightning.im.MessageRequestPacket;
import lightning.im.Packet;

import java.util.*;

public class StringToPacketUtil {

    public static Packet toPacket(String input) {
        if (input.startsWith("login:") || input.startsWith("LOGIN:")) {
            // login
            String usrAndPwd = input.substring(6);
            String[] tokens = usrAndPwd.split("/");
            LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
            loginRequestPacket.setUsername(tokens[0]);
            loginRequestPacket.setPassword(tokens[1]);
            return loginRequestPacket;
        } else if ((input.startsWith("cg:") || input.startsWith("CG:"))) {  // cg = create group
            String input2 = input.substring(3);
            String[] tokens = input2.split(" ");
            String groupName = tokens[0];
            List<String> members = Arrays.asList(tokens[1].split("/"));
            CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();
            createGroupRequestPacket.setGroupName(groupName);
            createGroupRequestPacket.setMembers(members);
            return createGroupRequestPacket;
        } else if ((input.startsWith("gc:") || input.startsWith("GC:"))) {  // cg = create group
            String input2 = input.substring(3);
            String[] tokens = input2.split(" ");
            String groupName = tokens[0];
            String message = tokens[1];

            GroupMessageRequestPacket groupMessageRequestPacket = new GroupMessageRequestPacket();
            groupMessageRequestPacket.setGroupName(groupName);
            groupMessageRequestPacket.setMessage(message);

            return groupMessageRequestPacket;
        } else {
            // chat
            MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
            messageRequestPacket.setMessage(input);
            return messageRequestPacket;
        }
    }

}
