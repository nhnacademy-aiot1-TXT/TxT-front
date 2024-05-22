package com.nhnacademy.front.controller;

import com.nhnacademy.front.adaptor.DeviceSettingAdapter;
import com.nhnacademy.front.adaptor.SensorAdapter;
import com.nhnacademy.front.adaptor.UserAdapter;
import com.nhnacademy.front.dto.*;
import com.nhnacademy.front.utils.AccessTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 어드민 권한만 접근할 수 있는 Controller
 */
@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserAdapter userAdapter;
    private final SensorAdapter sensorAdapter;
    private final DeviceSettingAdapter deviceSettingAdapter;

    @GetMapping
    public String admin() {
        return "redirect:/";
    }

    @GetMapping("/manage")
    public String manage(HttpServletRequest request, Model model,
                         @RequestParam(value = "statusParam", defaultValue = "userList") String statusParam,
                         @RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "size", defaultValue = "5") int size) {
        Page<UserDataResponse> users;
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);

        switch (statusParam) {
            case "pendingUser":
                users = userAdapter.findSortedUsers(accessToken, 4, page, size);
                model.addAttribute("actionUrl", "/admin/manage/permit");
                break;
            case "deactivateUser":
                users = userAdapter.findSortedUsers(accessToken, 3, page, size);
                model.addAttribute("actionUrl", "/admin/manage/permit");
                break;
            case "adminList":
                users = userAdapter.findSortedUserByRole(accessToken, page, size, 1);
                break;
            default:
                users = userAdapter.findAllUsers(accessToken, page, size);
                model.addAttribute("actionUrl", "/admin/manage/promotion");
                break;
        }

        model.addAttribute("users", users);
        model.addAttribute("statusSet", statusParam);

        return "manage";
    }

    //유저등록
    @PostMapping("/manage/permit")
    public String permitUser(HttpServletRequest request, @RequestParam("redirectUrl") String redirectUrl) {
        List<PermitUserRequest> permitUserRequests = new ArrayList<>();
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
        String[] selectedUserIds = request.getParameterValues("userIds");

        if (selectedUserIds != null) {
            for (String userId : selectedUserIds) {
                PermitUserRequest permitUserRequest = new PermitUserRequest(); // 각 반복마다 새 객체 생성
                permitUserRequest.setId(userId); // 유저 ID 설정
                permitUserRequests.add(permitUserRequest); // 리스트에 추가
            }
            userAdapter.permitUser(accessToken, permitUserRequests); // 사용자 허용 메서드 호출
        }

        return "redirect:" + redirectUrl.substring(redirectUrl.indexOf("/admin"));
    }

    @PostMapping("/manage/promotion")
    public String promotionUser(HttpServletRequest request, @RequestParam("redirectUrl") String redirectUrl) {
        List<PermitUserRequest> permitUserRequests = new ArrayList<>();
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
        String[] selectedUserIds = request.getParameterValues("userIds");

        if (selectedUserIds != null) {
            for (String userId : selectedUserIds) {
                PermitUserRequest permitUserRequest = new PermitUserRequest();
                permitUserRequest.setId(userId);
                permitUserRequests.add(permitUserRequest);
            }
            userAdapter.promotionUser(accessToken, permitUserRequests);
        }

        return "redirect:" + redirectUrl.substring(redirectUrl.indexOf("/admin"));
    }

    // 상세센서 정보
    @GetMapping("/detail-sensor-info")
    public String profile(HttpServletRequest request,
                          @RequestParam(value = "currentPlace", defaultValue = "class_a") String currentPlace,
                          Model model) {
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);

        model.addAttribute("accessToken", accessToken);
        model.addAttribute("currentPlace", currentPlace);
        model.addAttribute("placeList", deviceSettingAdapter.getPlaceList(accessToken));

        return "detailedSensor";
    }

    @GetMapping("/detail-sensor-info/log")
    public String sensorLog(HttpServletRequest request,
                            @RequestParam(value = "currentPlace", defaultValue = "class_a") String currentPlace,
                            @RequestParam(value = "sensorType", defaultValue = "temperature") String sensorType,
                            @RequestParam(value = "period", defaultValue = "day") String period,
                            Model model){

        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);

        model.addAttribute("currentPlace", currentPlace);
        model.addAttribute("sensorType", sensorType);
        model.addAttribute("period", period);
        model.addAttribute("placeList", deviceSettingAdapter.getPlaceList(accessToken));
        model.addAttribute("sensorDataList", sensorAdapter.getSensorData(accessToken, currentPlace, sensorType, period));

        return "dataLog";
    }
}
