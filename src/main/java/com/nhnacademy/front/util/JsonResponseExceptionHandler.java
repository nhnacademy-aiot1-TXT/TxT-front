package com.nhnacademy.front.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonResponseExceptionHandler {

    static ObjectMapper mapper = new ObjectMapper();

    private JsonResponseExceptionHandler(){
    }

    public static String title(RuntimeException e) throws JsonProcessingException {
        String jsonString = e.getMessage().substring(e.getMessage().indexOf("{"), e.getMessage().lastIndexOf("}") + 1);
        JsonNode root = mapper.readTree(jsonString);
        return root.get("title").asText();
    }

}
