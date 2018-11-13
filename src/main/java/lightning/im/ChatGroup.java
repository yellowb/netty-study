package lightning.im;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.*;

public class ChatGroup {

    private String groupName;

    private List<String> members;

    private ChannelGroup channelGroup;

    public ChatGroup(String groupName) {
        this.groupName = groupName;
        this.members = new ArrayList<>();
        this.channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public ChannelGroup getChannelGroup() {
        return channelGroup;
    }

    public void setChannelGroup(ChannelGroup channelGroup) {
        this.channelGroup = channelGroup;
    }

    public void addMember(String member, Channel channel) {
        this.members.add(member);
        this.channelGroup.add(channel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChatGroup chatGroup = (ChatGroup)o;
        return Objects.equals(groupName, chatGroup.groupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupName);
    }
}
