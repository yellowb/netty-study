package lightning.im;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.*;

public class PacketCodeC {

    public static final int MAGIC_NUMBER = 0x12345678;

    private static final Map<Byte, Class<? extends Packet>> CLASS_MAP = new HashMap<>();

    private static final Map<Byte, Serializer> SERIALIZER_MAP = new HashMap<>();

    static {
        CLASS_MAP.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        CLASS_MAP.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        CLASS_MAP.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        CLASS_MAP.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);

        SERIALIZER_MAP.put(SerializerAlgorithm.JSON, new JSONSerializer());
    }

    public ByteBuf encode(Packet packet) {
        return this.encode(ByteBufAllocator.DEFAULT, packet);
    }

    public ByteBuf encode(ByteBufAllocator alloc, Packet packet) {
        ByteBuf byteBuf = null;
        if (alloc != null) {
            byteBuf = alloc.ioBuffer();
        } else {
            byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        }

        this.encode(byteBuf, packet);

        return byteBuf;
    }

    public void encode(ByteBuf byteBuf, Packet packet) {
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // encoding
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    public Packet decode(ByteBuf byteBuf) {
        // skip magic number
        byteBuf.skipBytes(4);

        // skip version number
        byteBuf.skipBytes(1);

        // 序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();

        // command
        byte command = byteBuf.readByte();

        // body length
        int length = byteBuf.readInt();

        // body
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Class<? extends Packet> getRequestType(byte classType) {
        return CLASS_MAP.get(classType);
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return SERIALIZER_MAP.get(serializeAlgorithm);
    }

}
