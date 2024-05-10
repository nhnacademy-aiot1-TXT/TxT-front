package com.nhnacademy.front.dto;


import lombok.*;

import java.time.Instant;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TemperatureResponse {
    private Instant time;
    private Float maxTemperature;
    private Float minTemperature;
}
