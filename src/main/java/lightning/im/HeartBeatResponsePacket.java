package lightning.im;

public class HeartBeatResponsePacket extends Packet {
    @Override
    public byte getCommand() {
        return Command.HEART_BEAT_RESPONSE;
    }
}
