package com.nhnacademy.front.controller;

import com.nhnacademy.front.adaptor.DeviceSettingAdapter;
import com.nhnacademy.front.dto.DeviceResponse;
import com.nhnacademy.front.dto.PlaceResponse;
import com.nhnacademy.front.interceptor.LoginCheckInterceptor;
import com.nhnacademy.front.utils.RedisUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class ControlControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LoginCheckInterceptor loginCheckInterceptor;
    @MockBean
    private RedisUtil redisUtil;
    @MockBean
    private DeviceSettingAdapter deviceSettingAdapter;
    @Mock
    private HttpServletRequest request;

    private static final String ACCESS_TOKEN = "test token";
    private static final Long PLACE_ID = 1L;

    private static final String DEVICE_KEY = "device_power_status";
    private static final String LIGHT = "light:test place";
    private static final String AIR_CONDITIONER = "airconditioner:test place";
    private static final String AIR_CLEANER = "aircleaner:test place";
    private static final String AUTO_MODE = "auto_mode:test place";
    private static final String MODE = "mode";

    @BeforeEach
    void setUp() throws Exception {
        given(loginCheckInterceptor.preHandle(any(), any(), any())).willReturn(true);
        Cookie accessTokenCookie = new Cookie("accessToken", ACCESS_TOKEN);
        given(request.getCookies()).willReturn(new Cookie[]{accessTokenCookie});
    }

    @Test
    void control() throws Exception {
        given(deviceSettingAdapter.getPlaceList(anyString())).willReturn(List.of(PlaceResponse.builder().placeName("test place").build()));
        given(deviceSettingAdapter.getPlace(anyString(), anyLong())).willReturn(PlaceResponse.builder().placeName("test place").build());
        given(deviceSettingAdapter.getDeviceListByPlace(anyString(), anyLong())).willReturn(List.of(DeviceResponse.builder().deviceName("test device").aiMode(1).build()));
        given(redisUtil.getDeviceStatus(DEVICE_KEY, LIGHT)).willReturn(true);
        given(redisUtil.getDeviceStatus(DEVICE_KEY, AIR_CONDITIONER)).willReturn(true);
        given(redisUtil.getDeviceStatus(DEVICE_KEY, AIR_CLEANER)).willReturn(true);
        given(redisUtil.getMode(AUTO_MODE)).willReturn(true);

        mockMvc.perform(get("/control/1").cookie(new Cookie("accessToken", ACCESS_TOKEN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(ControlController.class))
                .andExpect(handler().methodName("control"))
                .andExpect(model().attribute("status", Map.of("light", true, "aircleaner", true, "airconditioner", true)))
                .andExpect(model().attribute(MODE, true))
                .andExpect(view().name("control"));
    }

    @Test
    void light() throws Exception {
        boolean isOn = true;
        String place = "test place";
        mockMvc.perform(get("/control/light")
                        .param("placeName", place)
                        .param("isOn", Boolean.toString(isOn)))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(ControlController.class))
                .andExpect(handler().methodName("light"))
                .andExpect(redirectedUrl("/control"));
    }

    @Test
    void airConditioner() throws Exception {
        boolean isOn = true;
        String place = "test place";
        mockMvc.perform(get("/control/air-conditioner")
                        .param("placeName", place)
                        .param("isOn", Boolean.toString(isOn)))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(ControlController.class))
                .andExpect(handler().methodName("airConditioner"))
                .andExpect(redirectedUrl("/control"));
    }

    @Test
    void airCleaner() throws Exception {
        boolean isOn = true;
        String place = "test place";
        mockMvc.perform(get("/control/air-cleaner")
                        .param("placeName", place)
                        .param("isOn", Boolean.toString(isOn)))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(ControlController.class))
                .andExpect(handler().methodName("airCleaner"))
                .andExpect(redirectedUrl("/control"));
    }

    @Test
    void aiMode() throws Exception {
        boolean isOn = true;
        String place = "test place";
        mockMvc.perform(get("/control/ai-mode")
                        .param("placeName", place)
                        .param("isOn", Boolean.toString(isOn)))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(ControlController.class))
                .andExpect(handler().methodName("aiMode"))
                .andExpect(redirectedUrl("/control"));
    }
}