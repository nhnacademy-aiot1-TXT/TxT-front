package com.nhnacademy.front.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
