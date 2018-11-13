package lightning.im;


import io.netty.channel.Channel;

import java.util.*;

public class GroupChatUtil {

    private static final Map<String, ChatGroup> CHAT_GROUPS = new HashMap<>();

    /**
     * 新建一个chat group并注册到系统中
     * @param groupName
     * @param members
     * @return
     */
    public static boolean createGroup(String groupName, List<String> members) {
        if (CHAT_GROUPS.containsKey(groupName) || members.isEmpty()) {
            return false;
        }
        ChatGroup chatGroup = new ChatGroup(groupName);
        for (String member : members) {
            if (LoginUtil.hasLogin(member)) {
                Channel loginUserChannel = LoginUtil.getLoginUserChannel(member);
                chatGroup.addMember(member, loginUserChannel);
            }
        }
        CHAT_GROUPS.put(groupName, chatGroup);
        return true;
    }

    public static ChatGroup getGroup(String groupName) {
        return CHAT_GROUPS.get(groupName);
    }

    public static boolean removeGroup(String groupName) {
        return CHAT_GROUPS.remove(groupName) != null;
    }
}
