package com.nhnacademy.front.service.impl;

import com.nhnacademy.front.dto.ValueMessage;
import com.nhnacademy.front.service.RabbitmqService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * RabbitmqService 구현 클래스
 *
 * @author parksangwon
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class RabbitmqServiceImpl implements RabbitmqService {
    private final RabbitTemplate rabbitTemplate;
    @Value("${spring.rabbitmq.device-control-exchange}")
    private String deviceControlExchange;
    private static final Integer TIME_TO_LIVE = 0;

    /**
     * 메시지를 message queue에 등록하기 위한 메서드
     *
     * @param valueMessage the message
     * @param routingKey   the routing key
     */
    @Override
    public void sendMessage(ValueMessage valueMessage, String routingKey) {
        rabbitTemplate.convertAndSend(deviceControlExchange, routingKey, valueMessage, message -> {
            message.getMessageProperties().setExpiration(TIME_TO_LIVE.toString());
            return message;
        });
    }
}
