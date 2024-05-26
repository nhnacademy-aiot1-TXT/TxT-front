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

    /**
     * index 메서드는 홈페이지로의 요청을 처리합니다.
     * 주어진 장소 코드를 기반으로 해당 장소를 설정하고, 장소 목록과 액세스 토큰을 뷰에 전달합니다.
     *
     * @param request HTTP 요청 객체
     * @param placeCode 장소 코드 (기본값: "class_a")
     * @param model 뷰에 전달할 데이터를 담은 모델 객체
     * @return 인덱스 페이지의 뷰 이름
     */

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
