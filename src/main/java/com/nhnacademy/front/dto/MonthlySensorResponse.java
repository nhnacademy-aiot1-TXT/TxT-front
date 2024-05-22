package com.nhnacademy.front.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlySensorResponse {
    private Instant time;
    @JsonAlias({"maxTemperature", "maxHumidity", "maxIllumination", "maxCo2"})
    private Float maxValue;
    @JsonAlias({"minTemperature", "minHumidity", "minIllumination", "minCo2"})
    private Float minValue;
}
