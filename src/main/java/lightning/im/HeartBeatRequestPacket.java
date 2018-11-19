package lightning.im;

public class HeartBeatRequestPacket extends Packet {
    @Override
    public byte getCommand() {
        return Command.HEART_BEAT_REQUEST;
    }
}
