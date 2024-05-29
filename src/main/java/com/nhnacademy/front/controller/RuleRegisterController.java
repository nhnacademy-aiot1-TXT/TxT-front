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

@Controller
@RequestMapping("/admin/rule")
@RequiredArgsConstructor
public class RuleRegisterController {
    private final DeviceSettingAdapter deviceSettingAdapter;

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
