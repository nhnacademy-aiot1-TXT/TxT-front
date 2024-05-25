package com.nhnacademy.front.controller;

import com.nhnacademy.front.adaptor.DeviceSettingAdapter;
import com.nhnacademy.front.dto.DeviceResponse;
import com.nhnacademy.front.dto.PlaceResponse;
import com.nhnacademy.front.dto.ValueMessage;
import com.nhnacademy.front.service.RabbitmqService;
import com.nhnacademy.front.utils.AccessTokenUtil;
import com.nhnacademy.front.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static final String AI_MODE = "ai_mode";
    private static final String CUSTOM_MODE = "custom_mode";
    private static final String REDIRECT_CONTROL = "redirect:/control";
    private static final String PREDICT_DATA = "predict_data";
    private final DeviceSettingAdapter deviceSettingAdapter;
    private final Map<String, String> deviceIconMap;

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

        Map<String, Object> statusMap = new HashMap<>();
        Map<String, Object> customMap = new HashMap<>();
        deviceList.forEach(deviceResponse -> {
            Object deviceStatus = redisUtil.getDeviceStatus(DEVICE_KEY, deviceResponse.getDeviceName().toLowerCase().concat(":").concat(currentPlace.getPlaceCode()));
            Object customMode = redisUtil.getMode(CUSTOM_MODE, currentPlace.getPlaceCode().concat("_").concat(deviceResponse.getDeviceName().toLowerCase()));
            statusMap.put(deviceResponse.getDeviceName().toLowerCase(), deviceStatus);
            customMap.put(deviceResponse.getDeviceName().toLowerCase(), customMode);
        });

        Object aiMode = redisUtil.getMode(AI_MODE, currentPlace.getPlaceCode().concat("_").concat(AIR_CONDITIONER));

        model.addAttribute("deviceIconMap", deviceIconMap);
        model.addAttribute("status", statusMap);
        model.addAttribute("placeList", placeList);
        model.addAttribute("currentPlace", currentPlace);
        model.addAttribute("deviceList", deviceList);
        model.addAttribute("aiMode", aiMode);
        model.addAttribute("custom", customMap);

        return "control";
    }

    @GetMapping("/light")
    public String light(@RequestParam String placeCode, @RequestParam Boolean isOn) {
        ValueMessage valueMessage = new ValueMessage(placeCode, isOn);

        rabbitmqService.sendMessage(valueMessage, ROUTE_KEY_PREFIX.concat(LIGHT));

        return REDIRECT_CONTROL;
    }

    @GetMapping("/air-conditioner")
    public String airConditioner(@RequestParam String placeCode, @RequestParam Boolean isOn) {
        ValueMessage valueMessage = new ValueMessage(placeCode, isOn);

        rabbitmqService.sendMessage(valueMessage, ROUTE_KEY_PREFIX.concat(AIR_CONDITIONER));

        return REDIRECT_CONTROL;
    }

    @GetMapping("/air-cleaner")
    public String airCleaner(@RequestParam String placeCode, @RequestParam Boolean isOn) {
        ValueMessage valueMessage = new ValueMessage(placeCode, isOn);

        rabbitmqService.sendMessage(valueMessage, ROUTE_KEY_PREFIX.concat(AIR_CLEANER));

        return REDIRECT_CONTROL;
    }

    @GetMapping("/ai-mode")
    public String aiMode(@RequestParam String placeCode, @RequestParam Boolean isOn) {
        redisUtil.setMode(AI_MODE, placeCode.concat("_").concat(AIR_CONDITIONER), isOn);

        if (Boolean.TRUE.equals(isOn))
            redisUtil.setMode(CUSTOM_MODE, placeCode.concat("_").concat(AIR_CONDITIONER), false);

        return REDIRECT_CONTROL;
    }

    @GetMapping("/custom-mode")
    public String customMode(@RequestParam String deviceName, @RequestParam String placeCode, @RequestParam Boolean isOn) {
        redisUtil.setMode(CUSTOM_MODE, placeCode.concat("_").concat(deviceName), isOn);

        return REDIRECT_CONTROL;
    }

    @ResponseBody
    @GetMapping("/ai-result")
    public ResponseEntity<Map<String, Object>> getAiResult() {
        List<String> fieldList = List.of("deviceName", "time", "indoorTemperature", "indoorHumidity", "outdoorTemperature", "outdoorHumidity", "totalPeopleCount", "result");
        Map<String, Object> aiResultMap = new HashMap<>();

        fieldList.forEach(s -> aiResultMap.put(s, redisUtil.getAiInfo(PREDICT_DATA, s)));

        return ResponseEntity.ok(aiResultMap);
    }
}
