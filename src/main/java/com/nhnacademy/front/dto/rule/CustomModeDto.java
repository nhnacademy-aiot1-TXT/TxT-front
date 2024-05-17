package com.nhnacademy.front.dto.rule;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomModeDto {
    private Map<MqttInDto, ConditionSetDto> mqttConditionMap;
    private Integer hour;
    private Integer minutes;
}
