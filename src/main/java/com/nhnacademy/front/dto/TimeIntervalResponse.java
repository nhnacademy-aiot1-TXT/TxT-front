package com.nhnacademy.front.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

/**
 * 탐지 시간 응답을 처리하기 위한 dto 클래스
 *
 * @author parksangwon
 * @version 1.0.0
 */
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
