package com.nhnacademy.front.controller;

import com.nhnacademy.front.adaptor.DeviceSettingAdapter;
import com.nhnacademy.front.dto.DeviceRequest;
import com.nhnacademy.front.dto.DeviceResponse;
import com.nhnacademy.front.dto.DeviceSensorRequest;
import com.nhnacademy.front.dto.DeviceSensorResponse;
import com.nhnacademy.front.mode.AircleanerMode;
import com.nhnacademy.front.utils.AccessTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/device/settings")
public class DeviceSettingController {

    private final DeviceSettingAdapter deviceSettingAdapter;

    @GetMapping("/{deviceName}")
    public String getDeviceSettingInfo(@PathVariable String deviceName, HttpServletRequest request, Model model) {
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
        DeviceResponse deviceResponse = deviceSettingAdapter.getDevice(accessToken, deviceName);
        List<DeviceSensorResponse> deviceSensorResponse = deviceSettingAdapter.getSensorList(accessToken, deviceResponse.getDeviceId());
        if (!deviceSensorResponse.isEmpty()) {
            model.addAttribute("deviceSensor", deviceSensorResponse.get(0));
        }
        model.addAttribute("device", deviceResponse);
        return "device-setting/setting-view";
    }

    @PostMapping("/{deviceName}")
    public String updateDeviceSettingInfo(HttpServletRequest request, @PathVariable String deviceName, String deviceId, int hour, int minute) {
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
        DeviceRequest deviceRequest = new DeviceRequest(deviceName, LocalTime.of(hour, minute));
        deviceSettingAdapter.updateDevice(accessToken, deviceId, deviceRequest);

        return "redirect:/device/settings/{deviceName}";

    }

    @PostMapping("/{deviceName}/{sensorName}")
    public String updateDeviceSensorSettingInfo(HttpServletRequest request, Model model, @PathVariable String deviceName, @PathVariable String sensorName, AircleanerMode mode) {
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
        DeviceSensorRequest deviceSensorRequest = new DeviceSensorRequest(deviceName, sensorName, mode.getOnValue(), mode.getOffValue());
        DeviceSensorResponse deviceSensorResponse = deviceSettingAdapter.getDeviceSensor(accessToken, deviceName, sensorName);
        deviceSettingAdapter.updateDeviceSensor(accessToken, deviceSensorResponse.getDeviceId(), deviceSensorResponse.getSensorId(), deviceSensorRequest);

        return "redirect:/device/settings/{deviceName}";
    }
}