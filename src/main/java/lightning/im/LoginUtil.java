package lightning.im;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import lightning.im2.Session;
import org.w3c.dom.Attr;

import java.util.*;

public class LoginUtil {

    private static final Map<String, Channel> LOGIN_USER_MAP = new HashMap<>();

    /**
     * 把密码验证通过的用户登记在系统中
     * @param session
     * @param channel
     */
    public static void login(Session session, Channel channel) {
        markAsLogin(channel);
        channel.attr(Attributes.SESSION).set(session);
        LOGIN_USER_MAP.put(session.getUsername(), channel);
    }

    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(Boolean.TRUE);
    }

    public static void logout(Channel channel) {
        Session session = channel.attr(Attributes.SESSION).get();
        if (session != null) {
            LOGIN_USER_MAP.remove(session.getUsername());
            markAsLogout(channel);
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static void markAsLogout(Channel channel) {
        channel.attr(Attributes.LOGIN).set(null);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> attr = channel.attr(Attributes.LOGIN);
        return Boolean.TRUE.equals(attr.get());
    }

    public static boolean hasLogin(String username) {
        return LOGIN_USER_MAP.containsKey(username);
    }

    public static Channel getLoginUserChannel(String username) {
        return LOGIN_USER_MAP.get(username);
    }

    public static Session getCurrentUserSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }
}
