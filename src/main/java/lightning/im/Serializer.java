package lightning.im;

public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    /**
     * 获取序列化算法
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * 序列化Java对象
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 反序列化
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
