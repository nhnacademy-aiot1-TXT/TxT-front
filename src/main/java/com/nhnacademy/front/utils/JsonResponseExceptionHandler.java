package com.nhnacademy.front.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON 응답 예외 처리 유틸리티 클래스.
 * TxT-user-management 의 GlobalExceptionHandler 에서 보내는 exception 형식에 따라 매핑.
 * Json 형태의 'title' 값에 메세지가 담겨져 온다.
 *
 * @author parksangwon
 * @version 1.0.0
 */
public class JsonResponseExceptionHandler {

    static ObjectMapper mapper = new ObjectMapper();

    private JsonResponseExceptionHandler() {
    }

    /**
     * 예외 객체에서 JSON 형식의 문자열을 추출하여 처리.
     *
     * @param e 처리할 RuntimeException 객체
     * @return JSON 문자열에서 추출한 "title" 속성 값 또는 전체 JSON 문자열
     * @throws JsonProcessingException JSON 처리 예외가 발생할 경우
     */
    public static String title(RuntimeException e) throws JsonProcessingException {
        String jsonString = e.getMessage().substring(e.getMessage().indexOf("{"), e.getMessage().lastIndexOf("}") + 1);
        JsonNode root = mapper.readTree(jsonString);
        if (root.has("title")) {
            return root.get("title").asText();
        }
        return root.toString();
    }

}
