package com.nhnacademy.front.adaptor;

import com.nhnacademy.front.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "common-api", url = "${gateway.url}", path = "/api/common")
public interface DeviceSettingAdapter {
    @GetMapping("/device")
    DeviceResponse getDevice(@RequestHeader("Authorization") String accessToken, @RequestParam String name);

    @GetMapping("/device-sensor/{deviceId}")
    List<DeviceSensorResponse> getSensorList(@RequestHeader("Authorization") String accessToken, @PathVariable Long deviceId);

    @PutMapping("/device/{deviceId}")
    DeviceResponse updateDevice(@RequestHeader("Authorization") String accessToken, @PathVariable String deviceId, @RequestBody DeviceRequest deviceRequest);

    @GetMapping("/device-sensor/sensor")
    DeviceSensorResponse getDeviceSensor(@RequestHeader("Authorization") String accessToken, @RequestParam String deviceName, @RequestParam String sensorName);

    @PutMapping("/device-sensor/{deviceId}/{sensorId}")
    DeviceSensorResponse updateDeviceSensor(@RequestHeader("Authorization") String accessToken, @PathVariable Long deviceId, @PathVariable Long sensorId, @RequestBody DeviceSensorRequest deviceSensorRequest);

    @GetMapping("/time-interval")
    TimeIntervalResponse getTimeInterval(@RequestHeader("Authorization") String accessToken, @RequestParam String sensorName);

    @PutMapping("/time-interval/{timeIntervalId}")
    TimeIntervalResponse updateTimeInterval(@RequestHeader("Authorization") String accessToken, @PathVariable Long timeIntervalId, @RequestBody TimeIntervalRequest timeIntervalRequest);

    @GetMapping("/place")
    List<PlaceResponse> getPlaceList(@RequestHeader("Authorization") String accessToken);
}
