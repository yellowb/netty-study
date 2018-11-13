package lightning.im;

import java.util.*;

public class CreateGroupRequestPacket extends Packet {

    private String groupName;

    private List<String> usernames;

    private String creator;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
