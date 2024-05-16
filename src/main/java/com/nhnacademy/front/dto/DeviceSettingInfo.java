package com.nhnacademy.front.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceSettingInfo {
    DeviceResponse device;
    DeviceSensorResponse deviceSensor;
}