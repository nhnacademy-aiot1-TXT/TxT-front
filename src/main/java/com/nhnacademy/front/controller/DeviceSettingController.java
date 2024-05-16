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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        List<DeviceResponse> deviceList;
        deviceList = deviceSettingAdapter.getDeviceListByPlace(accessToken, placeId);
        List<DeviceSettingInfo> deviceSettingList = deviceList.stream()
                .map(deviceResponse -> {
                    List<DeviceSensorResponse> deviceSensorResponseList = deviceSettingAdapter.getSensorList(accessToken, deviceResponse.getDeviceId());
                    if (deviceSensorResponseList.isEmpty()) {
                        return null;
                    }

                    return new DeviceSettingInfo(deviceResponse, deviceSensorResponseList.get(0));
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        model.addAttribute("currentPlace", currentPlace);
        model.addAttribute("placeList", placeList);
        model.addAttribute("deviceSettingList", deviceSettingList);
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