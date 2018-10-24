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
}
