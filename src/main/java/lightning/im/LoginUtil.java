package lightning.im;

import io.netty.channel.Channel;
import io.netty.util.Attribute;

public class LoginUtil {
    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(Boolean.TRUE);
    }

    public static void markAsLogout(Channel channel) {
        channel.attr(Attributes.LOGIN).set(null);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> attr = channel.attr(Attributes.LOGIN);
        return Boolean.TRUE.equals(attr.get());
    }
}
