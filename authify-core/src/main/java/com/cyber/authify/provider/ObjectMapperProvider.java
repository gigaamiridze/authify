package com.cyber.authify.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ObjectMapperProvider {

    @Autowired
    private ObjectMapper objectMapper;

    public String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Failed to serialize object to JSON", ex);
        }
    }

    public <T> T fromJson(String content, Class<T> valueType) {
        try {
            return objectMapper.readValue(content, valueType);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Failed to deserialize JSON", ex);
        }
    }
}
