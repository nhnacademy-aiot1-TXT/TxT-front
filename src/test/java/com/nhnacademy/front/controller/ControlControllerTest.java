package com.nhnacademy.front.controller;

import com.nhnacademy.front.interceptor.LoginCheckInterceptor;
import com.nhnacademy.front.utils.RedisUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
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
    private static final String DEVICE_KEY = "device_power_status";
    private static final String LIGHT = "light";
    private static final String AIR_CONDITIONER = "airconditioner";
    private static final String AIR_CLEANER = "aircleaner";
    private static final String AUTO_MODE = "auto_mode:airconditioner";
    private static final String MODE = "mode";

    @BeforeEach
    void setUp() throws Exception {
        given(loginCheckInterceptor.preHandle(any(), any(), any())).willReturn(true);
    }

    @Test
    void control() throws Exception {
        given(redisUtil.getDeviceStatus(DEVICE_KEY, LIGHT)).willReturn(true);
        given(redisUtil.getDeviceStatus(DEVICE_KEY, AIR_CONDITIONER)).willReturn(true);
        given(redisUtil.getDeviceStatus(DEVICE_KEY, AIR_CLEANER)).willReturn(true);
        given(redisUtil.getMode(AUTO_MODE)).willReturn(true);

        mockMvc.perform(get("/control"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(ControlController.class))
                .andExpect(handler().methodName("control"))
                .andExpect(model().attribute(LIGHT, true))
                .andExpect(model().attribute(AIR_CONDITIONER, true))
                .andExpect(model().attribute(AIR_CLEANER, true))
                .andExpect(model().attribute(MODE, true))
                .andExpect(view().name("control"));
    }

    @Test
    void light() throws Exception {
        boolean isOn = true;
        mockMvc.perform(get("/control/light")
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
        mockMvc.perform(get("/control/air-conditioner")
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
        mockMvc.perform(get("/control/air-cleaner")
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
        mockMvc.perform(get("/control/ai-mode")
                        .param("isOn", Boolean.toString(isOn)))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(ControlController.class))
                .andExpect(handler().methodName("aiMode"))
                .andExpect(redirectedUrl("/control"));
    }
}