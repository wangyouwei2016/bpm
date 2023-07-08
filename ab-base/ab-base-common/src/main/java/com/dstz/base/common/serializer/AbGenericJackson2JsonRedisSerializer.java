package com.dstz.base.common.serializer;

import cn.hutool.core.util.ArrayUtil;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * 重写jackson序列化器包含类型，增强字节序列判断
 *
 * @author wacxhs
 */
public class AbGenericJackson2JsonRedisSerializer extends GenericJackson2JsonRedisSerializer {

    /**
     * 类型字节数组
     */
    private static final byte TYPE_BYTES = 0x1;

    /**
     * 类型对象
     */
    private static final byte TYPE_OBJECT = 0x2;

    @Override
    public byte[] serialize(Object source) throws SerializationException {
        if (source == null) {
            return super.serialize(source);
        }
        byte[] dataBytes;
        byte dataType;
        if (source instanceof byte[]) {
            dataBytes = (byte[]) source;
            dataType = TYPE_BYTES;
        } else {
            dataBytes = super.serialize(source);
            dataType = TYPE_OBJECT;
        }
        // 增强数据类型
        byte[] dataBytes2 = new byte[dataBytes.length + 1];
        dataBytes2[0] = dataType;
        System.arraycopy(dataBytes, 0, dataBytes2, 1, dataBytes.length);
        return dataBytes2;
    }

    @Override
    public <T> T deserialize(byte[] source, Class<T> type) throws SerializationException {
        if (ArrayUtil.isEmpty(source)) {
            return null;
        }
        if (source[0] == TYPE_BYTES) {
            return (T) ArrayUtil.sub(source, 1, source.length);
        } else {
            return super.deserialize(ArrayUtil.sub(source, 1, source.length), type);
        }
    }
}
