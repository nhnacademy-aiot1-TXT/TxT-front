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


    /**
     * 침입 감지 정보를 조회하는 요청을 처리하는 매서드.
     *
     * @param request HTTP 요청 객체
     * @param model 뷰에 전달할 데이터를 담은 모델 객체
     * @return 설정 뷰 페이지의 뷰 이름
     */

    @GetMapping
    public String getDetectionInfo(HttpServletRequest request, Model model) {
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
        TimeIntervalResponse response = deviceSettingAdapter.getTimeInterval(accessToken, "occupancy");
        List<PlaceResponse> placeList = deviceSettingAdapter.getPlaceList(accessToken);

        model.addAttribute("placeList", placeList);
        model.addAttribute("detect", response);
        model.addAttribute("accessTokenTemp", AccessTokenUtil.findAccessTokenInRequest(request));

        return "device-setting/setting-view";
    }

    /**
     * 침입 감지 정보를 업데이트하는 요청을 처리하는 매서드.
     *
     * @param request HTTP 요청 객체
     * @param timeIntervalId 감지 시간 간격 ID
     * @param sensorId 센서 ID
     * @param sensorName 센서 이름
     * @param onHour 켜기 시간(시)
     * @param onMinute 켜기 시간(분)
     * @param offHour 끄기 시간(시)
     * @param offMinute 끄기 시간(분)
     * @return 감지 설정 페이지로의 리다이렉트 URL
     */

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