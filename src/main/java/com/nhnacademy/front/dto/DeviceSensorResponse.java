package com.nhnacademy.front.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 장치별 센서 응답을 처리하기 위한 dto 클래스
 *
 * @author parksangwon
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceSensorResponse {
    private Long deviceId;
    private Long sensorId;
    private String sensorName;
    private Float onValue;
    private Float offValue;
}
