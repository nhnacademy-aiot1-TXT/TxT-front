package com.nhnacademy.front.controller;

import com.nhnacademy.front.adaptor.DeviceSettingAdapter;
import com.nhnacademy.front.dto.*;
import com.nhnacademy.front.mode.AircleanerMode;
import com.nhnacademy.front.utils.AccessTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 장치 설정 API 요청을 처리하는 Controller 클래스
 *
 * @author parksangwon
 * @version 1.0.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/device/settings")
public class DeviceSettingController {
    private final DeviceSettingAdapter deviceSettingAdapter;

    /**
     * 기본 장소로 설정해서 장치 값 설정으로 리다이렉트하는 메서드
     *
     * @return 장치 값 설정 리다이렉트
     */
    @GetMapping
    public String settingView() {
        return "redirect:/device/settings/1";
    }


    /**
     * 특정 장소의 장치 설정 정보를 조회하는 요청을 처리하는 매서드.
     *
     * @param placeId 장소 ID
     * @param request HTTP 요청 객체
     * @param model   뷰에 전달할 데이터를 담은 모델 객체
     * @return 장치 설정 뷰 페이지의 뷰 이름
     */
    @GetMapping("/{placeId}")
    public String getDeviceSettingInfo(@PathVariable(required = false) Long placeId, HttpServletRequest request, Model model) {
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
        List<PlaceResponse> placeList = deviceSettingAdapter.getPlaceList(accessToken);
        PlaceResponse currentPlace = deviceSettingAdapter.getPlace(accessToken, placeId);
        List<DeviceResponse> deviceList = deviceSettingAdapter.getDeviceListByPlace(accessToken, placeId);
        Map<String, List<DeviceSensorResponse>> deviceSensorMap = new HashMap<>();
        deviceList.forEach(deviceResponse -> {
            List<DeviceSensorResponse> deviceSensorResponseList = deviceSettingAdapter.getSensorList(accessToken, deviceResponse.getDeviceId());
            if (deviceSensorResponseList != null) {
                deviceSensorMap.put(deviceResponse.getDeviceName(), deviceSensorResponseList);
            }
        });
        model.addAttribute("deviceList", deviceList);
        model.addAttribute("currentPlace", currentPlace);
        model.addAttribute("placeList", placeList);
        model.addAttribute("deviceSensorMap", deviceSensorMap);

        return "device-setting/setting-view";
    }

    /**
     * 특정 장소의 설정을 업데이트하는 요청을 처리하는 매서드.
     *
     * @param request   HTTP 요청 객체
     * @param placeId   장소 ID
     * @param placeName 장소 이름
     * @param hour      시간(시)
     * @param minute    시간(분)
     * @return 설정 페이지로의 리다이렉트 URL
     */
    @PostMapping("/place/{placeId}")
    public String updateDeviceSettingInfo(HttpServletRequest request, @PathVariable Long placeId, @RequestParam String placeName, int hour, int minute) {
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
        PlaceRequest placeRequest = new PlaceRequest(placeName, LocalTime.of(hour, minute));
        deviceSettingAdapter.updatePlace(accessToken, placeId, placeRequest);

        return "redirect:/device/settings/{placeId}";
    }

    /**
     * 특정 장치의 센서 설정을 업데이트하는 요청을 처리하는 매서드.
     *
     * @param request    HTTP 요청 객체
     * @param placeId    장소 ID
     * @param placeName  장소 이름
     * @param deviceName 장치 이름
     * @param sensorName 센서 이름
     * @param mode       장치 모드
     * @param onValue    활성화 값
     * @param offValue   비활성화 값
     * @return 설정 페이지로의 리다이렉트 URL
     */
    @PostMapping("/{placeId}/{deviceName}/{sensorName}")
    public String updateDeviceSensorSettingInfo(HttpServletRequest request, @PathVariable Long placeId, @RequestParam String placeName, @PathVariable String deviceName, @PathVariable String sensorName, @RequestParam(required = false) AircleanerMode mode, @RequestParam(required = false) Float onValue, @RequestParam(required = false) Float offValue) {
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
        DeviceSensorRequest deviceSensorRequest;
        if (Objects.nonNull(mode)) {
            deviceSensorRequest = new DeviceSensorRequest(deviceName, sensorName, placeName, mode.getOnValue(), mode.getOffValue());
        } else {
            deviceSensorRequest = new DeviceSensorRequest(deviceName, sensorName, placeName, onValue, offValue);
        }
        DeviceSensorResponse deviceSensorResponse = deviceSettingAdapter.getDeviceSensorByName(accessToken, deviceName, sensorName, placeName);
        deviceSettingAdapter.updateDeviceSensor(accessToken, deviceSensorResponse.getDeviceId(), deviceSensorResponse.getSensorId(), deviceSensorRequest);

        return "redirect:/device/settings/{placeId}";
    }
}