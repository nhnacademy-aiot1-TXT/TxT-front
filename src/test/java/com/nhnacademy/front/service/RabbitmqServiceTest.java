package com.nhnacademy.front.service;

import com.nhnacademy.front.dto.ValueMessage;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class RabbitmqServiceTest {
    @Autowired
    private RabbitmqService rabbitmqService;
    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Test
    void sendMessage() {
        ValueMessage valueMessage = new ValueMessage(true);
        String routingKey = "test.key";

        rabbitmqService.sendMessage(valueMessage, routingKey);

        verify(rabbitTemplate, times(1))
                .convertAndSend(
                        anyString(),
                        anyString(),
                        any(ValueMessage.class),
                        any(MessagePostProcessor.class)
                );
    }
}