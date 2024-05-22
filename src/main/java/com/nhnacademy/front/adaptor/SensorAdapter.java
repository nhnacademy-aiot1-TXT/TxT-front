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
    @GetMapping("/api/sensor/temperature/day")
    List<DailySensorResponse> getDailyTemperatures(@RequestHeader("Authorization") String accessToken,
                                                   @RequestParam String place);

    @GetMapping("/api/sensor/temperature/week")
    List<WeeklySensorResponse> getWeeklyTemperatures(@RequestHeader("Authorization") String accessToken,
                                                    @RequestParam String place);

    @GetMapping("/api/sensor/temperature/month")
    List<MonthlySensorResponse> getMonthlyTemperatures(@RequestHeader("Authorization") String accessToken,
                                                     @RequestParam String place);

    @GetMapping("/api/sensor/illumination/day")
    List<DailySensorResponse> getDailyIllumination(@RequestHeader("Authorization") String accessToken,
                                                    @RequestParam String place);

    @GetMapping("/api/sensor/illumination/week")
    List<WeeklySensorResponse> getWeeklyIllumination(@RequestHeader("Authorization") String accessToken,
                                                     @RequestParam String place);

    @GetMapping("/api/sensor/illumination/month")
    List<MonthlySensorResponse> getMonthlyIllumination(@RequestHeader("Authorization") String accessToken,
                                                      @RequestParam String place);

    @GetMapping("/api/sensor/humidity/day")
    List<DailySensorResponse> getDailyHumidity(@RequestHeader("Authorization") String accessToken,
                                            @RequestParam String place);

    @GetMapping("/api/sensor/humidity/week")
    List<WeeklySensorResponse> getWeeklyHumidity(@RequestHeader("Authorization") String accessToken,
                                             @RequestParam String place);


    @GetMapping("/api/sensor/humidity/month")
    List<MonthlySensorResponse> getMonthlyHumidity(@RequestHeader("Authorization") String accessToken,
                                              @RequestParam String place);

    @GetMapping("/api/sensor/co2/day")
    List<DailySensorResponse> getDailyCo2(@RequestHeader("Authorization") String accessToken,
                                  @RequestParam String place);


    @GetMapping("/api/sensor/co2/week")
    List<WeeklySensorResponse> getWeeklyCo2(@RequestHeader("Authorization") String accessToken,
                                   @RequestParam String place);


    @GetMapping("/api/sensor/co2/month")
    List<MonthlySensorResponse> getMonthlyCo2(@RequestHeader("Authorization") String accessToken,
                                    @RequestParam String place);


}
