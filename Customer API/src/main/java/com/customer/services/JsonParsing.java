package com.customer.services;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParsing {
    private static ObjectMapper objectMapper = getDefaultObjectMapper();

    private static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        return defaultObjectMapper;
    }
    
    public static JsonNode StringToJson(String src) throws IOException {
        return objectMapper.readTree(src);
    }
    
    public static JsonNode ObjectToJson(Object a) {
    	return objectMapper.valueToTree(a);
    }
    
    public static <A> A JsonToObject(JsonNode node , Class<A> clazz) throws IOException {
    	return objectMapper.treeToValue(node, clazz);
    }
    
    public static <A> A StringToObject(String src, Class<A> clazz) throws IOException{
    	JsonNode node = StringToJson(src);
    	return objectMapper.treeToValue(node, clazz);
    }
   
}
