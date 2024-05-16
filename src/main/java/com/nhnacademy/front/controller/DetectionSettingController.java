package com.nhnacademy.front.controller;

import com.nhnacademy.front.adaptor.DeviceSettingAdapter;
import com.nhnacademy.front.dto.PlaceResponse;
import com.nhnacademy.front.dto.TimeIntervalRequest;
import com.nhnacademy.front.dto.TimeIntervalResponse;
import com.nhnacademy.front.utils.AccessTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/detect/settings")
public class DetectionSettingController {
    private final DeviceSettingAdapter deviceSettingAdapter;

    @GetMapping
    public String getDetectionInfo(HttpServletRequest request, Model model) {
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
        TimeIntervalResponse response = deviceSettingAdapter.getTimeInterval(accessToken, "occupancy");
        List<PlaceResponse> placeList = deviceSettingAdapter.getPlaceList(accessToken);

        model.addAttribute("placeList", placeList);
        model.addAttribute("detect", response);
        return "device-setting/setting-view";
    }

    @PostMapping
    public String updateDetectionInfo(
            HttpServletRequest request,
            @RequestParam Long timeIntervalId,
            @RequestParam Long sensorId,
            @RequestParam String sensorName,
            int onHour, int onMinute,
            int offHour, int offMinute
    ) {
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
        TimeIntervalRequest timeIntervalRequest = new TimeIntervalRequest(sensorId, sensorName, LocalTime.of(onHour, onMinute), LocalTime.of(offHour, offMinute));

        deviceSettingAdapter.updateTimeInterval(accessToken, timeIntervalId, timeIntervalRequest);

        return "redirect:/detect/settings";
    }
}