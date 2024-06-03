package com.nhnacademy.front.adaptor;


import com.nhnacademy.front.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 장치 세팅 API 요청을 보내기 위한 adapter 클래스
 *
 * @author parksangwon
 * @version 1.0.0
 */
@FeignClient(value = "common-api", url = "${gateway.url}", path = "/api/common")
public interface DeviceSettingAdapter {
    /**
     * 장소 정보를 조회하기 위한 메서드
     *
     * @param accessToken the access token
     * @param placeId     the place id
     * @return the place
     */
    @GetMapping("/place/{placeId}")
    PlaceResponse getPlace(@RequestHeader("Authorization") String accessToken, @PathVariable Long placeId);

    /**
     * 장소에 있는 장치 정보를 조회하기 위한 메서드
     *
     * @param accessToken the access token
     * @param placeId     the place id
     * @return the device list by place
     */
    @GetMapping("/device/devices/{placeId}")
    List<DeviceResponse> getDeviceListByPlace(@RequestHeader("Authorization") String accessToken, @PathVariable Long placeId);

    /**
     * 장소에 있는 장치 정보를 조회하기 위한 메서드
     *
     * @param accessToken the access token
     * @param placeName   the place name
     * @param name        the name
     * @return the device
     */
    @GetMapping("/device")
    DeviceResponse getDevice(@RequestHeader("Authorization") String accessToken, @RequestParam String placeName, @RequestParam String name);

    /**
     * 장치별 센서를 조회하기 위한 메서드
     *
     * @param accessToken the access token
     * @param deviceId    the device id
     * @return the sensor list
     */
    @GetMapping("/device-sensor/{deviceId}")
    List<DeviceSensorResponse> getSensorList(@RequestHeader("Authorization") String accessToken, @PathVariable Long deviceId);

    /**
     * 장치를 수정하기 위한 메서드
     *
     * @param accessToken   the access token
     * @param deviceId      the device id
     * @param deviceRequest the device request
     * @return the device response
     */
    @PutMapping("/device/{deviceId}")
    DeviceResponse updateDevice(@RequestHeader("Authorization") String accessToken, @PathVariable String deviceId, @RequestBody DeviceRequest deviceRequest);

    /**
     * 장치별 센서를 단일로 조회하기 위한 메서드
     *
     * @param accessToken the access token
     * @param deviceId    the device id
     * @param sensorId    the sensor id
     * @return the device sensor by id
     */
    @GetMapping("/device-sensor/{deviceId}/{sensorId}")
    DeviceSensorResponse getDeviceSensorById(@RequestHeader("Authorization") String accessToken, @PathVariable Long deviceId, @PathVariable Long sensorId);

    /**
     * 장치별 센서를 단일로 조회하기 위한 메서드
     *
     * @param accessToken the access token
     * @param deviceName  the device name
     * @param sensorName  the sensor name
     * @param placeName   the place name
     * @return the device sensor by name
     */
    @GetMapping("/device-sensor/sensor")
    DeviceSensorResponse getDeviceSensorByName(@RequestHeader("Authorization") String accessToken, @RequestParam String deviceName, @RequestParam String sensorName, @RequestParam String placeName);

    /**
     * 장치별 센서를 업데이트하기 위한 메서드
     *
     * @param accessToken         the access token
     * @param deviceId            the device id
     * @param sensorId            the sensor id
     * @param deviceSensorRequest the device sensor request
     * @return the device sensor response
     */
    @PutMapping("/device-sensor/{deviceId}/{sensorId}")
    DeviceSensorResponse updateDeviceSensor(@RequestHeader("Authorization") String accessToken, @PathVariable Long deviceId, @PathVariable Long sensorId, @RequestBody DeviceSensorRequest deviceSensorRequest);

    /**
     * 장소를 수정하기 위한 메서드
     *
     * @param accessToken  the access token
     * @param placeId      the place id
     * @param placeRequest the place request
     * @return the place response
     */
    @PutMapping("/place/{placeId}")
    PlaceResponse updatePlace(@RequestHeader("Authorization") String accessToken, @PathVariable Long placeId, @RequestBody PlaceRequest placeRequest);

    /**
     * 탐지 시간을 조회하는 메서드
     *
     * @param accessToken the access token
     * @param sensorName  the sensor name
     * @return the time interval
     */
    @GetMapping("/time-interval")
    TimeIntervalResponse getTimeInterval(@RequestHeader("Authorization") String accessToken, @RequestParam String sensorName);

    /**
     * 탐지 시간을 수정하는 메서드
     *
     * @param accessToken         the access token
     * @param timeIntervalId      the time interval id
     * @param timeIntervalRequest the time interval request
     * @return the time interval response
     */
    @PutMapping("/time-interval/{timeIntervalId}")
    TimeIntervalResponse updateTimeInterval(@RequestHeader("Authorization") String accessToken, @PathVariable Long timeIntervalId, @RequestBody TimeIntervalRequest timeIntervalRequest);

    /**
     * 장소들을 조회하는 메서드
     *
     * @param accessToken the access token
     * @return the place list
     */
    @GetMapping("/place")
    List<PlaceResponse> getPlaceList(@RequestHeader("Authorization") String accessToken);
}
