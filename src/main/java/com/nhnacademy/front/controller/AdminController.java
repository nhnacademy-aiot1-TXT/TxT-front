package com.nhnacademy.front.controller;

import com.nhnacademy.front.adaptor.SensorAdapter;
import com.nhnacademy.front.adaptor.UserAdapter;
import com.nhnacademy.front.dto.*;
import com.nhnacademy.front.dto.IlluminationResponse.IlluminationResponse;
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

    @GetMapping
    public String admin() {
        return "redirect:/";
    }

    @GetMapping("/dtsensor")
    public String profile(HttpServletRequest request, Model model) {
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
        UserDataResponse user = userAdapter.getUserData(accessToken);

        model.addAttribute("user", user);
        model.addAttribute("accessToken", AccessTokenUtil.findAccessTokenInRequest(request));

        return "detailedSensor";
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
    public String permitUser(HttpServletRequest request) {
        List<PermitUserRequest> permitUserRequests = new ArrayList<>();
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
        String[] selectedUserIds = request.getParameterValues("userIds");

        if (selectedUserIds != null) {
            for (String userId : selectedUserIds) {
                System.out.println("Selected User ID: " + userId);
                PermitUserRequest permitUserRequest = new PermitUserRequest(); // 각 반복마다 새 객체 생성
                permitUserRequest.setId(userId); // 유저 ID 설정
                permitUserRequests.add(permitUserRequest); // 리스트에 추가
            }
            userAdapter.permitUser(accessToken, permitUserRequests); // 사용자 허용 메서드 호출
        }

        return "redirect:/admin/manage";
    }

    @PostMapping("/manage/promotion")
    public String promotionUser(HttpServletRequest request) {
        List<PermitUserRequest> permitUserRequests = new ArrayList<>();
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
        String[] selectedUserIds = request.getParameterValues("userIds");

        if (selectedUserIds != null) {
            for (String userId : selectedUserIds) {
                System.out.println("Selected User ID: " + userId);
                PermitUserRequest permitUserRequest = new PermitUserRequest();
                permitUserRequest.setId(userId);
                permitUserRequests.add(permitUserRequest);
            }
            userAdapter.promotionUser(accessToken, permitUserRequests);
        }

        return "redirect:/admin/manage";
    }

    // 상세센서 정보
    @GetMapping("temperature/week")
    public String weeklyTemperature(HttpServletRequest request, Model model) {
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);

        List<TemperatureResponse> tempWeek = sensorAdapter.getWeeklyTemperatures(accessToken);
        model.addAttribute("temperatureList", tempWeek);

        return "sensor-log/log-temperature";
    }

    @GetMapping("illumination/week")
    public String weeklyIllumination(HttpServletRequest request, Model model) {
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);

        List<IlluminationResponse> illuminationWeek = sensorAdapter.getWeeklyIllumination(accessToken);
        model.addAttribute("illuminationWeek", illuminationWeek);

        return "sensor-log/log-birghtness";
    }

    @GetMapping("humidity/week")
    public String weeklyHumidity(HttpServletRequest request, Model model) {
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);

        List<HumidityResponse> humidityDaily = sensorAdapter.getWeeklyHumidity(accessToken);
        model.addAttribute("humidityList", humidityDaily);

        return "sensor-log/log-humidity";
    }

    @GetMapping("co2/week")
    public String weeklyCo2(HttpServletRequest request, Model model) {
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);

        List<Co2Response> Co2Week = sensorAdapter.getWeeklyCo2(accessToken);
        model.addAttribute("co2List", Co2Week);

        return "sensor-log/log-co2";
    }
}
