package com.nhnacademy.front.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.front.adaptor.SensorAdapter;
import com.nhnacademy.front.dto.Co2Response;
import com.nhnacademy.front.dto.HumidityResponse;
import com.nhnacademy.front.dto.IlluminationResponse.IlluminationResponse;
import com.nhnacademy.front.dto.TemperatureResponse;
import com.nhnacademy.front.dto.UserDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
                         @RequestParam(value = "statusId", defaultValue = "1") int statusId,
                         @RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "size", defaultValue = "5") int size) throws JsonProcessingException {
        String accessToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "accessToken".equals(cookie.getName()))
                .findFirst()
                .orElse(null)
                .getValue();

        Page<UserDataResponse> users = userAdapter.findSortedUsers(accessToken, statusId, page, size);

        List<UserDataResponse> usersList = users.getContent();


        ObjectMapper mapper = new ObjectMapper();
        String usersJson = mapper.writeValueAsString(usersList);


        model.addAttribute("usersJson", usersJson);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", users.getTotalPages());

        return "manage";
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
