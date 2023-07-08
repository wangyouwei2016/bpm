package com.dstz.base.common.serializer;

import java.io.IOException;

import com.dstz.base.common.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 用于解决 字符串形式的对象字段 序列化成 JSON字段
 * 
 * @author jeff
 *
 */
public class RawJsonSerializer extends JsonSerializer<String> {

	@Override
	public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeObject(JsonUtils.toJSONNode(value));
	}
}