package com.nhnacademy.front.controller;

import com.nhnacademy.front.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/control")
public class ControlController {
    private final RedisUtil redisUtil;
    private static final String DEVICE_KEY = "device_power_status";
    private static final String AIR_CONDITIONER = "airconditioner";
    private static final String AIR_CLEANER = "aircleaner";
    private static final String LIGHT = "light";
    private static final String MODE = "mode";
    private static final String AUTO_MODE = "auto_mode:airconditioner";
    private static final String REDIRECT_CONTROL = "redirect:/control";

    @GetMapping
    public String control(Model model) {
        Object lightStatus = redisUtil.getDeviceStatus(DEVICE_KEY, LIGHT);
        Object airConditionerStatus = redisUtil.getDeviceStatus(DEVICE_KEY, AIR_CONDITIONER);
        Object airCleanerStatus = redisUtil.getDeviceStatus(DEVICE_KEY, AIR_CLEANER);
        Object autoMode = redisUtil.getMode(AUTO_MODE);

        model.addAttribute(LIGHT, lightStatus);
        model.addAttribute(AIR_CONDITIONER, airConditionerStatus);
        model.addAttribute(AIR_CLEANER, airCleanerStatus);
        model.addAttribute(MODE, autoMode);

        return "control";
    }

    @GetMapping("/light")
    public String light(@RequestParam Boolean isOn) {
        redisUtil.setDeviceStatus(DEVICE_KEY, LIGHT, isOn);

        return REDIRECT_CONTROL;
    }

    @GetMapping("/air-conditioner")
    public String airConditioner(@RequestParam Boolean isOn) {
        redisUtil.setDeviceStatus(DEVICE_KEY, AIR_CONDITIONER, isOn);

        return REDIRECT_CONTROL;
    }

    @GetMapping("/air-cleaner")
    public String airCleaner(@RequestParam Boolean isOn) {
        redisUtil.setDeviceStatus(DEVICE_KEY, AIR_CLEANER, isOn);

        return REDIRECT_CONTROL;
    }

    @GetMapping("/ai-mode")
    public String aiMode(@RequestParam Boolean isOn) {
        redisUtil.setMode(AUTO_MODE, isOn);

        return REDIRECT_CONTROL;
    }
}
