package lightning.im;

import io.netty.buffer.ByteBuf;
import org.junit.Test;

import static org.junit.Assert.*;

public class PacketCodeCTest {

    @Test
    public void should_fully_equals_after_encode_decode() {
        LoginRequestPacket loginReq = new LoginRequestPacket();
        loginReq.setUserId(10086);
        loginReq.setUsername("Yellow");
        loginReq.setPassword("12345");

        PacketCodeC codec = new PacketCodeC();
        ByteBuf byteBuf = codec.encode(loginReq);

        LoginRequestPacket anotherLoginReq = (LoginRequestPacket)codec.decode(byteBuf);

        assertTrue(loginReq.getUserId() == anotherLoginReq.getUserId() &&
            loginReq.getUsername().equals(anotherLoginReq.getUsername()) &&
            loginReq.getPassword().equals(anotherLoginReq.getPassword()));
    }

}