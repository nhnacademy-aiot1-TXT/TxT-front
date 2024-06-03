package com.nhnacademy.front.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * 센서 응답을 처리하기 위한 dto 클래스
 *
 * @author parksangwon
 * @version 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SensorResponse {
    private Instant time;
    @JsonAlias({"maxTemperature", "maxHumidity", "maxIllumination", "maxCo2"})
    private Float maxValue;
    @JsonAlias({"minTemperature", "minHumidity", "minIllumination", "minCo2"})
    private Float minValue;
}
