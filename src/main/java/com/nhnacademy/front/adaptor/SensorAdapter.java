package com.nhnacademy.front.adaptor;

import com.nhnacademy.front.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Sensor 관련 기능을 수행하는 FeignClient 인터페이스
 */
@FeignClient(value = "sensor-data", url = "${gateway.url}")
public interface SensorAdapter {
    @GetMapping("/api/sensor/{sensorType}/{period}")
    List<SensorResponse> getSensorData(@RequestHeader("Authorization") String accessToken,
                                       @RequestParam String place,
                                       @PathVariable String sensorType,
                                       @PathVariable String period);

}
