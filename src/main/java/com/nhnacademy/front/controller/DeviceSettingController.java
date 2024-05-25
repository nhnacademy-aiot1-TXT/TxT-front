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

@Controller
@RequiredArgsConstructor
@RequestMapping("/device/settings")
public class DeviceSettingController {
    private final DeviceSettingAdapter deviceSettingAdapter;

    @GetMapping
    public String settingView() {
        return "redirect:/device/settings/1";
    }

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

    @PostMapping("/place/{placeId}")
    public String updateDeviceSettingInfo(HttpServletRequest request, @PathVariable Long placeId, @RequestParam String placeName, int hour, int minute) {
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
        PlaceRequest placeRequest = new PlaceRequest(placeName, LocalTime.of(hour, minute));
        deviceSettingAdapter.updatePlace(accessToken, placeId, placeRequest);

        return "redirect:/device/settings/{placeId}";

    }

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