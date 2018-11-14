package lightning.im;

public class GroupMessageResponsePacket extends Packet {

    public static final byte GROUP_MSG_SENT_SUCCEED = 1;

    public static final byte GROUP_MSG_SENT_FAILED = 2;

    private byte responseCode;

    private String groupName;

    private String message;

    private String sender;

    private String error;

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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public byte getCommand() {
        return Command.GROUP_MESSAGE_RESPONSE;
    }
}
