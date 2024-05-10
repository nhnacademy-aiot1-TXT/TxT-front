package com.nhnacademy.front.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.front.adaptor.SensorAdapter;
import com.nhnacademy.front.dto.*;
import com.nhnacademy.front.dto.IlluminationResponse.IlluminationResponse;
import com.nhnacademy.front.utils.AccessTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.nhnacademy.front.adaptor.UserAdapter;

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
        String accessToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "accessToken".equals(cookie.getName()))
                .findFirst()
                .orElse(null)
                .getValue();

        UserDataResponse user = userAdapter.getUserData(accessToken);
        model.addAttribute("user", user);

        return "detailedSensor";
    }


    @GetMapping("/manage")
    public String manage(HttpServletRequest request, Model model,
                         @RequestParam(value = "statusId", defaultValue = "4") int statusId,
                         @RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "size", defaultValue = "5") int size) throws JsonProcessingException {

        Page<UserDataResponse> users;

        String accessToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "accessToken".equals(cookie.getName()))
                .findFirst()
                .orElse(null)
                .getValue();


        if (statusId == 99) {
            users = userAdapter.findAllUsers(accessToken, page, size);
        } else if (statusId == 100) {
            users = userAdapter.findSortedUserByRole(accessToken, page, size, 1);
        } else {
            users = userAdapter.findSortedUsers(accessToken, statusId, page, size);
        }


        System.out.println(users);

//        List<UserDataResponse> usersList = users.getContent();
//
//
//        ObjectMapper mapper = new ObjectMapper();
//        String usersJson = mapper.writeValueAsString(usersList);


        model.addAttribute("users", users);
        model.addAttribute("statSet", statusId);


//        model.addAttribute("usersJson", usersJson);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", users.getTotalPages());

        return "manage";
    }


    //유저등록

    @PostMapping("/manage/permit")
    public String permitUser(HttpServletRequest request, Model model) {

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

    // 상세센서 정보

    @GetMapping("temperature/week")
    public String weeklyTemperature(HttpServletRequest request, Model model) {

        String accessToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "accessToken".equals(cookie.getName()))
                .findFirst()
                .orElse(null)
                .getValue();


        List<TemperatureResponse> tempWeek = sensorAdapter.getWeeklyTemperatures(accessToken);
        model.addAttribute("temperatureList", tempWeek);


        return "sensor-log/log-temperature";
    }

    @GetMapping("illumination/week")
    public String weeklyIllumination(HttpServletRequest request, Model model) {

        String accessToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "accessToken".equals(cookie.getName()))
                .findFirst()
                .orElse(null)
                .getValue();


        List<IlluminationResponse> illuminationWeek = sensorAdapter.getWeeklyIllumination(accessToken);
        model.addAttribute("illuminationWeek", illuminationWeek);


        return "sensor-log/log-birghtness";
    }


    @GetMapping("humidity/week")
    public String weeklyHumidity(HttpServletRequest request, Model model) {


        String accessToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "accessToken".equals(cookie.getName()))
                .findFirst()
                .orElse(null)
                .getValue();


        List<HumidityResponse> humidityDaily = sensorAdapter.getWeeklyHumidity(accessToken);

        model.addAttribute("humidityList", humidityDaily);

        return "sensor-log/log-humidity";
    }

    @GetMapping("co2/week")
    public String weeklyCo2(HttpServletRequest request, Model model) {


        String accessToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "accessToken".equals(cookie.getName()))
                .findFirst()
                .orElse(null)
                .getValue();


        List<Co2Response> Co2Week = sensorAdapter.getWeeklyCo2(accessToken);

        model.addAttribute("co2List", Co2Week);


        return "sensor-log/log-co2";
    }


}
