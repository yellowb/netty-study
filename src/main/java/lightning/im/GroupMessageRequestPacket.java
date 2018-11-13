package lightning.im;

public class GroupMessageRequestPacket extends Packet {

    private String groupName;

    private String message;

    private String sender;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public byte getCommand() {
        return Command.GROUP_MESSAGE_REQUEST;
    }
}
