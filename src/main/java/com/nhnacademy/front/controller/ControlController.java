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
    private static final String MODE = "mode";
    private static final String AUTO_MODE = "auto_mode:";
    private static final String REDIRECT_CONTROL = "redirect:/control";
    private final DeviceSettingAdapter deviceSettingAdapter;
    private final Map<String, String> deviceIconMap;

    @GetMapping
    public String controlView() {
        return "redirect:/control/1";
    }

    /**
     * 주어진 장소 ID에 해당하는 제어 페이지를 렌더링합니다.
     *
     * @param request HTTP 요청 객체
     * @param placeId 장소 ID
     * @param model 뷰에 전달할 데이터를 담은 모델 객체
     * @return 컨트롤 페이지의 뷰 이름
     */

    @GetMapping("/{placeId}")
    public String control(HttpServletRequest request, @PathVariable Long placeId, Model model) {
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);

        List<PlaceResponse> placeList = deviceSettingAdapter.getPlaceList(accessToken);
        PlaceResponse currentPlace = deviceSettingAdapter.getPlace(accessToken, placeId);
        List<DeviceResponse> deviceList = deviceSettingAdapter.getDeviceListByPlace(accessToken, placeId);
        boolean aiMode = deviceList.stream().anyMatch(deviceResponse -> deviceResponse.getAiMode() == 1);

        Object lightStatus = redisUtil.getDeviceStatus(DEVICE_KEY, LIGHT.concat(":").concat(currentPlace.getPlaceCode()));
        Object airConditionerStatus = redisUtil.getDeviceStatus(DEVICE_KEY, AIR_CONDITIONER.concat(":").concat(currentPlace.getPlaceCode()));
        Object airCleanerStatus = redisUtil.getDeviceStatus(DEVICE_KEY, AIR_CLEANER.concat(":").concat(currentPlace.getPlaceCode()));
        Object autoMode = redisUtil.getMode(AUTO_MODE.concat(currentPlace.getPlaceCode()));

        Map<String, Object> statusMap = new HashMap<>();
        statusMap.put(LIGHT, lightStatus);
        statusMap.put(AIR_CONDITIONER, airConditionerStatus);
        statusMap.put(AIR_CLEANER, airCleanerStatus);

        model.addAttribute("deviceIconMap", deviceIconMap);
        model.addAttribute("status", statusMap);
        model.addAttribute("placeList", placeList);
        model.addAttribute("currentPlace", currentPlace);
        model.addAttribute("deviceList", deviceList);
        model.addAttribute("aiMode", aiMode);
        model.addAttribute(MODE, autoMode);

        return "control";
    }

    /**
     * 조명을 제어하기 위한 요청을 처리합니다.
     * 주어진 장소 코드와 켜기/끄기 상태를 받아와서 해당 장치에 대한 제어 메시지를 RabbitMQ를 통해 전송합니다.
     *
     * @param placeCode 장소 코드
     * @param isOn 조명 상태 (켜짐: true, 꺼짐: false)
     * @return 제어 페이지로의 리다이렉트 URL
     */

    @GetMapping("/light")
    public String light(@RequestParam String placeCode, @RequestParam Boolean isOn) {
        ValueMessage valueMessage = new ValueMessage(placeCode, isOn);

        rabbitmqService.sendMessage(valueMessage, ROUTE_KEY_PREFIX.concat(LIGHT));

        return REDIRECT_CONTROL;
    }

    /**
     * 에어컨을 제어하기 위한 요청을 처리합니다.
     * 주어진 장소 코드와 켜기/끄기 상태를 받아와서 해당 장치에 대한 제어 메시지를 RabbitMQ를 통해 전송합니다.
     *
     * @param placeCode 장소 코드
     * @param isOn 에어컨 상태 (켜짐: true, 꺼짐: false)
     * @return 제어 페이지로의 리다이렉트 URL
     */

    @GetMapping("/air-conditioner")
    public String airConditioner(@RequestParam String placeCode, @RequestParam Boolean isOn) {
        ValueMessage valueMessage = new ValueMessage(placeCode, isOn);

        rabbitmqService.sendMessage(valueMessage, ROUTE_KEY_PREFIX.concat(AIR_CONDITIONER));

        return REDIRECT_CONTROL;
    }

    /**
     * 공기 청정기를 제어하기 위한 요청을 처리합니다.
     * 주어진 장소 코드와 켜기/끄기 상태를 받아와서 해당 장치에 대한 제어 메시지를 RabbitMQ를 통해 전송합니다.
     *
     * @param placeCode 장소 코드
     * @param isOn 공기 청정기 상태 (켜짐: true, 꺼짐: false)
     * @return 제어 페이지로의 리다이렉트 URL
     */

    @GetMapping("/air-cleaner")
    public String airCleaner(@RequestParam String placeCode, @RequestParam Boolean isOn) {
        ValueMessage valueMessage = new ValueMessage(placeCode, isOn);

        rabbitmqService.sendMessage(valueMessage, ROUTE_KEY_PREFIX.concat(AIR_CLEANER));

        return REDIRECT_CONTROL;
    }

    /**
     * 인공지능 모드를 설정하기 위한 요청을 처리합니다.
     * 주어진 장소 코드와 켜기/끄기 상태를 받아와서 해당 장소의 인공지능 모드를 설정합니다.
     *
     * @param placeCode 장소 코드
     * @param isOn 인공지능 모드 상태 (활성화: true, 비활성화: false)
     * @return 제어 페이지로의 리다이렉트 URL
     */

    @GetMapping("/ai-mode")
    public String aiMode(@RequestParam String placeCode, @RequestParam Boolean isOn) {
        redisUtil.setMode(AUTO_MODE.concat(placeCode), isOn);

        return REDIRECT_CONTROL;
    }
}
