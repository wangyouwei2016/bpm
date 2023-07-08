package com.dstz.component.j2cache.serialization;

import com.dstz.base.common.serializer.AbGenericJackson2JsonRedisSerializer;
import net.oschina.j2cache.util.Serializer;

import java.io.IOException;

/**
 * jackson json 序列化器
 *
 * @author wacxhs
 */
public class JacksonJsonSerializer implements Serializer {

    private final AbGenericJackson2JsonRedisSerializer serializer = new AbGenericJackson2JsonRedisSerializer();

    @Override
    public String name() {
        return "json";
    }

    @Override
    public byte[] serialize(Object obj) throws IOException {
        return serializer.serialize(obj);
    }

    @Override
    public Object deserialize(byte[] dataBytes) throws IOException {
        return serializer.deserialize(dataBytes);
    }
}
