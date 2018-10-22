package lightning.im;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class PacketCodeC {

    private static final int MAGIC_NUMBER = 0x12345678;

    public ByteBuf encode(Packet packet) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // encoding
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

}
