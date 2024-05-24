package com.nhnacademy.front.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis 관련 유틸리티 클래스
 */
@Component
@RequiredArgsConstructor
public class RedisUtil {
    private final RedisTemplate<String, Object> redisTemplateBlackList;
    private final RedisTemplate<String, Object> redisTemplateDevice;

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

    public Object getMode(String key, String hashKey) {
        return redisTemplateDevice.opsForHash().get(key, hashKey);
    }

    public void setMode(String key, String hashKey, boolean value) {
        redisTemplateDevice.opsForHash().put(key, hashKey, value);
    }

    public Object getDeviceStatus(String key, String hashKey) {
        return redisTemplateDevice.opsForHash().get(key, hashKey);
    }
}