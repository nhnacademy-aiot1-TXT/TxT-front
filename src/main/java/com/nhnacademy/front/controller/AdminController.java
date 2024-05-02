package com.nhnacademy.front.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.front.adaptor.SensorAdapter;
import com.nhnacademy.front.dto.HumidityResponse;
import com.nhnacademy.front.dto.UserDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

import com.nhnacademy.front.adaptor.UserAdapter;
import org.springframework.web.bind.annotation.RequestParam;

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
                         @RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "size", defaultValue = "5") int size) throws JsonProcessingException {
        String accessToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "accessToken".equals(cookie.getName()))
                .findFirst()
                .orElse(null)
                .getValue();

        Page<UserDataResponse> users = userAdapter.findSortedUsers(accessToken, 4L, page, size);

        List<UserDataResponse> usersList = users.getContent();


        ObjectMapper mapper = new ObjectMapper();
        String usersJson = mapper.writeValueAsString(usersList);


        model.addAttribute("usersJson", usersJson);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", users.getTotalPages());

        return "manage";
    }

    // 상세센서 정보

    @GetMapping("/week")
    public String weeklyTemperature(HttpServletRequest request, Model model) {


        return "sensor-log/temperature-log";
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

        return "sensor-log/humidity-log";
    }
}
