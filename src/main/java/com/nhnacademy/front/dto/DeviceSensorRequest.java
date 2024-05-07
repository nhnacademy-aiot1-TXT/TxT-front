package com.nhnacademy.front.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceSensorRequest {
    private String deviceName;
    private String sensorName;
    private Float onValue;
    private Float offValue;
}
