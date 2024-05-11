package com.nhnacademy.front.controller;

import com.nhnacademy.front.adaptor.DeviceSettingAdapter;
import com.nhnacademy.front.dto.TimeIntervalRequest;
import com.nhnacademy.front.dto.TimeIntervalResponse;
import com.nhnacademy.front.utils.AccessTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/detect/settings")
public class DetectionSettingController {
    private final DeviceSettingAdapter deviceSettingAdapter;

    @GetMapping("/{deviceName}")
    public String getDetectionInfo(HttpServletRequest request, @PathVariable String deviceName, Model model) {
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
        TimeIntervalResponse response = deviceSettingAdapter.getTimeInterval(accessToken, deviceName);

        model.addAttribute("detect", response);
        return "device-setting/setting-view";
    }

    @PostMapping("/{deviceName}")
    public String updateDetectionInfo(
            HttpServletRequest request,
            @PathVariable String deviceName,
            @RequestParam Long timeIntervalId,
            @RequestParam Long sensorId,
            @RequestParam String sensorName,
            int onHour, int onMinute,
            int offHour, int offMinute
    ) {
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
        TimeIntervalRequest timeIntervalRequest = new TimeIntervalRequest(sensorId, sensorName, LocalTime.of(onHour, onMinute), LocalTime.of(offHour, offMinute));

        deviceSettingAdapter.updateTimeInterval(accessToken, timeIntervalId, timeIntervalRequest);

        return "redirect:/detect/settings/{deviceName}";
    }
}
