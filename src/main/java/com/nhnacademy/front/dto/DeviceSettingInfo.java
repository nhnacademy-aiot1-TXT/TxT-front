package com.nhnacademy.front.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 장치 세팅 정보를 처리하기 위한 dto 클래스
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceSettingInfo {
    DeviceResponse device;
    DeviceSensorResponse deviceSensor;
}
