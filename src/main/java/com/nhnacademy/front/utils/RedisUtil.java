package com.nhnacademy.front.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis 관련 유틸리티 클래스
 *
 * @author parksangwon
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
public class RedisUtil {
    private final RedisTemplate<String, Object> redisTemplateBlackList;
    private final RedisTemplate<String, Object> redisTemplateDevice;
    private final RedisTemplate<String, String> redisTemplateAI;

    /**
     * 블랙리스트에 항목을 추가하는 메서드
     *
     * @param key          추가할 항목의 키
     * @param o            추가할 항목의 값
     * @param milliSeconds 값의 유효 시간(밀리초 단위)
     */
    public void setBlackList(String key, Object o, Long milliSeconds) {
        redisTemplateBlackList.setValueSerializer(new Jackson2JsonRedisSerializer(o.getClass()));
        redisTemplateBlackList.opsForValue().set(key, o, milliSeconds, TimeUnit.MILLISECONDS);
    }

    /**
     * AI/CUSTOM Mode 상태를 반환하는 메서드
     *
     * @param key     the key
     * @param hashKey the hash key
     * @return the mode
     */
    public Object getMode(String key, String hashKey) {
        return redisTemplateDevice.opsForHash().get(key, hashKey) == null ? Boolean.FALSE : redisTemplateDevice.opsForHash().get(key, hashKey);
    }

    /**
     * AI/CUSTOM Mode 상태를 저장하는 메서드
     *
     * @param key     the key
     * @param hashKey the hash key
     * @param value   the value
     */
    public void setMode(String key, String hashKey, boolean value) {
        redisTemplateDevice.opsForHash().put(key, hashKey, value);
    }

    /**
     * 장치 상태를 반환하는 메서드
     *
     * @param key     the key
     * @param hashKey the hash key
     * @return the device status
     */
    public Object getDeviceStatus(String key, String hashKey) {
        return redisTemplateDevice.opsForHash().get(key, hashKey) == null ? Boolean.FALSE : redisTemplateDevice.opsForHash().get(key, hashKey);
    }

    /**
     * AI 예측 결과를 반환하는 메서드
     *
     * @param key     the key
     * @param hashKey the hash key
     * @return the ai info
     */
    public Object getAiInfo(String key, String hashKey) {
        return redisTemplateAI.opsForHash().get(key, hashKey);
    }
}