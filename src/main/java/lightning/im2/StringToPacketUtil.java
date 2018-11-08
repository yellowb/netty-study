package lightning.im2;

import lightning.im.LoginRequestPacket;
import lightning.im.MessageRequestPacket;
import lightning.im.Packet;

public class StringToPacketUtil {

    public static Packet toPacket(String input) {
        if (input.startsWith("login:") || input.startsWith("LOGIN:")) {
            // login
            String usrAndPwd = input.substring(6);
            String[] tokens = usrAndPwd.split("/");
            LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
            loginRequestPacket.setUsername(tokens[0]);
            loginRequestPacket.setPassword(tokens[1]);
            return loginRequestPacket;
        } else {
            // chat
            MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
            messageRequestPacket.setMessage(input);
            return messageRequestPacket;
        }
    }

}
