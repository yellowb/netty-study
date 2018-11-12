package lightning.im;

/**
 * IM 指令常量
 */
public interface Command {
    /**
     * 登录请求
     */
    byte LOGIN_REQUEST = 1;

    /**
     * 登录应答
     */
    byte LOGIN_RESPONSE = 2;

    /**
     * Client -> Server chat packet
     */
    byte MESSAGE_REQUEST = 3;

    /**
     * Server -> Client chat packet
     */
    byte MESSAGE_RESPONSE = 4;

    /**
     * 建群请求
     */
    byte CREATE_GROUP_REQUEST = 5;

    /**
     * 建群响应
     */
    byte CREATE_GROUP_RESPONSE = 6;

    /**
     * 群聊消息(sender -> server)
     */
    byte GROUP_MESSAGE_REQUEST = 7;

    /**
     * 群聊消息(server -> receivers)
     */
    byte GROUP_MESSAGE_RESPONSE = 8;

}
