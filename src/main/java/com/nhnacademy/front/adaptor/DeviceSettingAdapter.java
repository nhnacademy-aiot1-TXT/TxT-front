package com.nhnacademy.front.adaptor;

import com.nhnacademy.front.dto.DeviceRequest;
import com.nhnacademy.front.dto.DeviceResponse;
import com.nhnacademy.front.dto.DeviceSensorRequest;
import com.nhnacademy.front.dto.DeviceSensorResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "common-api", url = "${gateway.url}", path = "/api/common")
public interface DeviceSettingAdapter {
    @GetMapping("/device")
    DeviceResponse getDevice(@RequestHeader("Authorization") String accessToken, @RequestParam String name);

    @PutMapping("/device/{deviceId}")
    DeviceResponse updateDevice(@RequestHeader("Authorization") String accessToken, @PathVariable String deviceId, @RequestBody DeviceRequest deviceRequest);

    @GetMapping("/device-sensor/sensor")
    DeviceSensorResponse getDeviceSensor(@RequestHeader("Authorization") String accessToken, @RequestParam String deviceName, @RequestParam String sensorName);

    @PutMapping("/device-sensor/{deviceId}/{sensorId}")
    DeviceSensorResponse updateDeviceSensor(@RequestHeader("Authorization") String accessToken, @PathVariable Long deviceId, @PathVariable Long sensorId, @RequestBody DeviceSensorRequest deviceSensorRequest);
}
