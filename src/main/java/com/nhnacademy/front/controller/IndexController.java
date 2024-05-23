package com.nhnacademy.front.controller;

import com.nhnacademy.front.adaptor.DeviceSettingAdapter;
import com.nhnacademy.front.dto.PlaceResponse;
import com.nhnacademy.front.utils.AccessTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final DeviceSettingAdapter deviceSettingAdapter;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        @RequestParam(value = "placeCode", defaultValue = "class_a") String placeCode,
                        Model model) {
        List<PlaceResponse> placeList = deviceSettingAdapter.getPlaceList(AccessTokenUtil.findAccessTokenInRequest(request));

        for (PlaceResponse p : placeList) {
            if (p.getPlaceCode().equals(placeCode)) {
                model.addAttribute("currentPlace", p);
            }
        }

        model.addAttribute("accessTokenTemp", AccessTokenUtil.findAccessTokenInRequest(request));
        model.addAttribute("placeList", placeList);

        return "index";
    }
}
