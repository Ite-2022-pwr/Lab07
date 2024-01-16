package pl.pwr.ite.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class DataParser {
    private static DataParser INSTANCE = null;

    private final ObjectMapper objectMapper;

    public DataParser() {
        var objectMapper = new ObjectMapper();
        objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.objectMapper = objectMapper;
    }

    public <E> E deserialzie(ByteBuffer buffer, Class<E> dataType) {
        return this.deserialize(deserialize(buffer), dataType);
    }

    public String deserialize(ByteBuffer buffer) {
        return new String(buffer.array(), StandardCharsets.UTF_8).trim();
    }

    public <E> E deserialize(String data, Class<E> dataType) {
        try {
            return objectMapper.readerFor(dataType).readValue(data, dataType);
        } catch (IOException ex) {
            throw new IllegalArgumentException(String.format("Couldn't deserialize object from data %s", data), ex);
        }
    }

    public <E> String serialize(E data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (IOException ex) {
            throw new IllegalArgumentException(String.format("Couldn't deserialize object from data %s", data), ex);
        }
    }

    public static DataParser getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new DataParser();
        }
        return INSTANCE;
    }
}
