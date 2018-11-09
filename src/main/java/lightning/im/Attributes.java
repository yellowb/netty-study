package lightning.im;

import io.netty.util.AttributeKey;
import lightning.im2.Session;

public interface Attributes {

    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");

}
