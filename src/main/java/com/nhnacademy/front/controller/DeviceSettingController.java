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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/device/settings")
public class DeviceSettingController {

    private final DeviceSettingAdapter deviceSettingAdapter;

    @GetMapping({"/{deviceName}", ""})
    public String getDeviceSettingInfo(@PathVariable(required = false) String deviceName, HttpServletRequest request, Model model) {
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
        DeviceResponse deviceResponse;
        List<DeviceSensorResponse> deviceSensorResponse;
        if (Objects.isNull(deviceName)) {
            deviceName = "airconditioner";
        }
        deviceResponse = deviceSettingAdapter.getDevice(accessToken, deviceName);
        deviceSensorResponse = deviceSettingAdapter.getSensorList(accessToken, deviceResponse.getDeviceId());
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
    public String updateDeviceSensorSettingInfo(HttpServletRequest request, @PathVariable String deviceName, @PathVariable String sensorName, @RequestParam(required = false) AircleanerMode mode, @RequestParam(required = false) Float onValue, @RequestParam(required = false) Float offValue) {
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
        DeviceSensorRequest deviceSensorRequest;
        if (Objects.nonNull(mode)) {
            deviceSensorRequest = new DeviceSensorRequest(deviceName, sensorName, mode.getOnValue(), mode.getOffValue());
        } else {
            deviceSensorRequest = new DeviceSensorRequest(deviceName, sensorName, onValue, offValue);
        }
        DeviceSensorResponse deviceSensorResponse = deviceSettingAdapter.getDeviceSensor(accessToken, deviceName, sensorName);
        deviceSettingAdapter.updateDeviceSensor(accessToken, deviceSensorResponse.getDeviceId(), deviceSensorResponse.getSensorId(), deviceSensorRequest);

        return "redirect:/device/settings/{deviceName}";
    }
}