package com.nhnacademy.front.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceResponse {
    Long placeId;
    Long deviceId;
    String deviceName;
    Integer aiMode;
}
