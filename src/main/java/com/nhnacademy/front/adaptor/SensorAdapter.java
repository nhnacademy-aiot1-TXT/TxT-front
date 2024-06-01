package com.nhnacademy.front.adaptor;

import com.nhnacademy.front.dto.SensorResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 센서 관련 API 요청을 보내기 위한 adapter 클래스
 *
 * @author parksangwon
 * @version 1.0.0
 */
@FeignClient(value = "sensor-data", url = "${gateway.url}")
public interface SensorAdapter {
    /**
     * 센서 데이터를 조회하는 메서드
     *
     * @param accessToken the access token
     * @param place       the place
     * @param sensorType  the sensor type
     * @param period      the period
     * @return the sensor data
     */
    @GetMapping("/api/sensor/{sensorType}/{period}")
    List<SensorResponse> getSensorData(@RequestHeader("Authorization") String accessToken,
                                       @RequestParam String place,
                                       @PathVariable String sensorType,
                                       @PathVariable String period);

}
