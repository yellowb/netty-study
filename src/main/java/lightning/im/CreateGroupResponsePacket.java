package lightning.im;

import java.util.*;

public class CreateGroupResponsePacket extends Packet {

    public static final byte CREATE_GROUP_SUCCEED = 1;

    public static final byte CREATE_GROUP_FAILED = 2;

    private byte responseCode;

    private String groupName;

    private List<String> usernames;

    private String creator;

    public byte getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(byte responseCode) {
        this.responseCode = responseCode;
    }

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
        return Command.CREATE_GROUP_RESPONSE;
    }
}
