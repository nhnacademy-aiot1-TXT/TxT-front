package com.nhnacademy.front.controller;

import com.nhnacademy.front.adaptor.DeviceSettingAdapter;
import com.nhnacademy.front.dto.DeviceResponse;
import com.nhnacademy.front.dto.PlaceResponse;
import com.nhnacademy.front.dto.ValueMessage;
import com.nhnacademy.front.service.RabbitmqService;
import com.nhnacademy.front.utils.AccessTokenUtil;
import com.nhnacademy.front.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/control")
public class ControlController {
    private final RedisUtil redisUtil;
    private final RabbitmqService rabbitmqService;
    private static final String DEVICE_KEY = "device_power_status";
    private static final String ROUTE_KEY_PREFIX = "txt.";
    private static final String LIGHT = "light";
    private static final String AIR_CONDITIONER = "airconditioner";
    private static final String AIR_CLEANER = "aircleaner";
    private static final String MODE = "mode";
    private static final String AUTO_MODE = "auto_mode:";
    private static final String REDIRECT_CONTROL = "redirect:/control";
    private final DeviceSettingAdapter deviceSettingAdapter;

    @GetMapping
    public String controlView() {
        return "redirect:/control/1";
    }

    @GetMapping("/{placeId}")
    public String control(HttpServletRequest request, @PathVariable Long placeId, Model model) {
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);

        List<PlaceResponse> placeList = deviceSettingAdapter.getPlaceList(accessToken);
        PlaceResponse currentPlace = deviceSettingAdapter.getPlace(accessToken, placeId);
        List<DeviceResponse> deviceList = deviceSettingAdapter.getDeviceListByPlace(accessToken, placeId);
        boolean aiMode = deviceList.stream().anyMatch(deviceResponse -> deviceResponse.getAiMode() == 1);

        Object lightStatus = redisUtil.getDeviceStatus(DEVICE_KEY, LIGHT.concat(":").concat(currentPlace.getPlaceName()));
        Object airConditionerStatus = redisUtil.getDeviceStatus(DEVICE_KEY, AIR_CONDITIONER.concat(":").concat(currentPlace.getPlaceName()));
        Object airCleanerStatus = redisUtil.getDeviceStatus(DEVICE_KEY, AIR_CLEANER.concat(":").concat(currentPlace.getPlaceName()));
        Object autoMode = redisUtil.getMode(AUTO_MODE.concat(currentPlace.getPlaceName()));


        model.addAttribute("placeList", placeList);
        model.addAttribute("currentPlace", currentPlace);
        model.addAttribute("deviceList", deviceList);
        model.addAttribute("aiMode", aiMode);
        model.addAttribute(LIGHT, lightStatus);
        model.addAttribute(AIR_CONDITIONER, airConditionerStatus);
        model.addAttribute(AIR_CLEANER, airCleanerStatus);
        model.addAttribute(MODE, autoMode);

        return "control";
    }

    @GetMapping("/light")
    public String light(@RequestParam String placeName, @RequestParam Boolean isOn) {
        ValueMessage valueMessage = new ValueMessage(placeName, isOn);

        rabbitmqService.sendMessage(valueMessage, ROUTE_KEY_PREFIX.concat(LIGHT));

        return REDIRECT_CONTROL;
    }

    @GetMapping("/air-conditioner")
    public String airConditioner(@RequestParam String placeName, @RequestParam Boolean isOn) {
        ValueMessage valueMessage = new ValueMessage(placeName, isOn);

        rabbitmqService.sendMessage(valueMessage, ROUTE_KEY_PREFIX.concat(AIR_CONDITIONER));

        return REDIRECT_CONTROL;
    }

    @GetMapping("/air-cleaner")
    public String airCleaner(@RequestParam String placeName, @RequestParam Boolean isOn) {
        ValueMessage valueMessage = new ValueMessage(placeName, isOn);

        rabbitmqService.sendMessage(valueMessage, ROUTE_KEY_PREFIX.concat(AIR_CLEANER));

        return REDIRECT_CONTROL;
    }

    @GetMapping("/ai-mode")
    public String aiMode(@RequestParam String placeName, @RequestParam Boolean isOn) {
        redisUtil.setMode(AUTO_MODE.concat(placeName), isOn);

        return REDIRECT_CONTROL;
    }
}
