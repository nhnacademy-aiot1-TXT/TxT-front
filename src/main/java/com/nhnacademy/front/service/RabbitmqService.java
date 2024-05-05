package com.nhnacademy.front.service;

import com.nhnacademy.front.dto.ValueMessage;

public interface RabbitmqService {
    void sendMessage(ValueMessage message, String routingKey);
}
