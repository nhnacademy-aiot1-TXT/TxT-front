package com.nhnacademy.front.adaptor;

import com.nhnacademy.front.dto.*;
import com.nhnacademy.front.page.RestPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User 관련 기능을 수행하는 FeignClient 인터페이스
 */
@FeignClient(value = "sensor-data", url = "${gateway.url}")
public interface SensorAdapter {


    @GetMapping("/api/sensor/humidity/week")
    List<HumidityResponse> getWeeklyHumidity(@RequestHeader("Authorization") String accessToken);

    @GetMapping("/api/sensor/co2/week")
    List<Co2Response> getWeeklyCo2(@RequestHeader("Authorization") String accessToken);

    @GetMapping("/api/sensor/temperature/week")
    List<TemperatureResponse> getWeeklyTemperatures(@RequestHeader("Authorization") String accessToken);

}
