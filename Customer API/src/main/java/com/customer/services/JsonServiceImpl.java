package com.customer.services;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

public class JsonServiceImpl implements JsonService{
	
	private ObjectMapper mapper;
	
	@Inject
	public JsonServiceImpl(ObjectMapper mapper) {
		this.mapper = mapper;
	}
	
	//EXCLUIR
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
    //EXCLUIR FIM

	@Override
	public <T> T fromJson(String json, Class<T> classType) {
		try {
			return mapper.readValue(json, classType);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String toJson(Object T){
		try {
			return mapper.writeValueAsString(T);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
   
}
