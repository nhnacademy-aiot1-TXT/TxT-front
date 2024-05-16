package com.nhnacademy.front.controller;

import com.nhnacademy.front.adaptor.DeviceSettingAdapter;
import com.nhnacademy.front.utils.AccessTokenUtil;
import com.nhnacademy.front.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final DeviceSettingAdapter deviceSettingAdapter;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        @RequestParam(value = "place", defaultValue = "class_a") String place,
                        Model model) {

        model.addAttribute("accessTokenTemp", AccessTokenUtil.findAccessTokenInRequest(request));
        model.addAttribute("place", place);
        model.addAttribute("placeList", deviceSettingAdapter.getPlaceList(AccessTokenUtil.findAccessTokenInRequest(request)));

        return "index";
    }
}
