package com.dstz.base.common.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

/**
 * 用于解决 Json 反序列化时对象 字段中存在数组或者对象  转成String 报错的问题
 * 
 * @author jeff
 *
 */
public class RawJsonDeserializer  extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser jp, DeserializationContext ctxt)
           throws IOException, JsonProcessingException {
    	return jp.getCodec().readTree(jp).toString();
    }
}