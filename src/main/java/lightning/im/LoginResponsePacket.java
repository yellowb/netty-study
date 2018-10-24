package lightning.im;

/**
 * 登录应答数据包
 */
public class LoginResponsePacket extends Packet {

    public static final byte LOGIN_PASSED = 1;

    public static final byte LOGIN_DENIED = 2;

    private byte loginResponse;

    private String errMsg;

    public byte getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(byte loginResponse) {
        this.loginResponse = loginResponse;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
