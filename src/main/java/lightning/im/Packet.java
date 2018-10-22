package lightning.im;

/**
 * IM 网络包抽象类
 */
public abstract class Packet {

    /**
     * Default version = 1
     */
    private byte version = 1;

    /**
     * 返回网络包包含的指令
     * @return
     */
    public abstract byte getCommand();
}
