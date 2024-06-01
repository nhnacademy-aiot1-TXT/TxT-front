package com.nhnacademy.front.controller;

import com.nhnacademy.front.adaptor.DeviceSettingAdapter;
import com.nhnacademy.front.dto.DeviceResponse;
import com.nhnacademy.front.dto.PlaceResponse;
import com.nhnacademy.front.utils.AccessTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 규칙 등록 페이지를 보여주기 위한 Controller 클래스
 *
 * @author parksangwon
 * @version 1.0.0
 */
@Controller
@RequestMapping("/admin/rule")
@RequiredArgsConstructor
public class RuleRegisterController {
    private final DeviceSettingAdapter deviceSettingAdapter;

    /**
     * 규칙 등록 페이지를 보여주기 위한 메서드
     *
     * @param request the request
     * @param model   the model
     * @return the string
     */
    @GetMapping("/register")
    public String ruleRegister(HttpServletRequest request, Model model) {
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
        List<PlaceResponse> placeList = deviceSettingAdapter.getPlaceList(accessToken);
        List<DeviceResponse> deviceList = deviceSettingAdapter.getDeviceListByPlace(accessToken, 1L);

        model.addAttribute("deviceList", deviceList);
        model.addAttribute("placeList", placeList);

        return "rule-register";
    }
}
