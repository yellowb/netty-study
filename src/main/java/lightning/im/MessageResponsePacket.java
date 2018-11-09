package lightning.im;

public class MessageResponsePacket extends Packet {

    private String message;

    private String fromUsername;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    @Override
    public byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
