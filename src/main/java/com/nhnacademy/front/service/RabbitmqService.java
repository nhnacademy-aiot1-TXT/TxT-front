package com.nhnacademy.front.service;

import com.nhnacademy.front.dto.ValueMessage;

/**
 * message queue관련 작업을 처리하기 위한 인터페이스
 *
 * @author parksangwon
 * @version 1.0.0
 */
public interface RabbitmqService {
    /**
     * 메시지를 등록하기 위한 메서드
     *
     * @param message    the message
     * @param routingKey the routing key
     */
    void sendMessage(ValueMessage message, String routingKey);
}
