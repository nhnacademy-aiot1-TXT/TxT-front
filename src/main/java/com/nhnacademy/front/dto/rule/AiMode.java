package com.nhnacademy.front.dto.rule;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AiMode {
    private List<MqttInDto> mqttInInfos;
    private Integer minutes;
}
