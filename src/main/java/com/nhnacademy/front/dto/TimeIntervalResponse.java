package com.nhnacademy.front.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeIntervalResponse {
    private Long timeIntervalId;
    private Long sensorId;
    private String sensorName;
    private LocalTime begin;
    private LocalTime end;
}
