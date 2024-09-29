// package com.nhnacademy.front.controller;

// import com.nhnacademy.front.adaptor.DeviceSettingAdapter;
// import com.nhnacademy.front.dto.DeviceResponse;
// import com.nhnacademy.front.dto.PlaceResponse;
// import com.nhnacademy.front.interceptor.LoginCheckInterceptor;
// import com.nhnacademy.front.utils.JwtUtil;
// import com.nhnacademy.front.utils.RedisUtil;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mock;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.security.authentication.TestingAuthenticationToken;
// import org.springframework.test.web.servlet.MockMvc;

// import javax.servlet.http.Cookie;
// import javax.servlet.http.HttpServletRequest;
// import java.util.List;
// import java.util.Map;

// import static org.hamcrest.Matchers.equalTo;
// import static org.mockito.ArgumentMatchers.*;
// import static org.mockito.BDDMockito.given;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @AutoConfigureMockMvc
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
// class ControlControllerTest {
//     @Autowired
//     private MockMvc mockMvc;
//     @MockBean
//     private LoginCheckInterceptor loginCheckInterceptor;
//     @MockBean
//     private JwtUtil jwtUtil;
//     @MockBean
//     private RedisUtil redisUtil;
//     @MockBean
//     private DeviceSettingAdapter deviceSettingAdapter;
//     @Mock
//     private HttpServletRequest request;

//     private static final String ACCESS_TOKEN = "test token";
//     private static final String DEVICE_KEY = "device_power_status";
//     private static final String PREDICT_DATA = "predict_data";

//     @BeforeEach
//     void setUp() throws Exception {
//         given(loginCheckInterceptor.preHandle(any(), any(), any())).willReturn(true);
//         Cookie accessTokenCookie = new Cookie("accessToken", ACCESS_TOKEN);
//         given(request.getCookies()).willReturn(new Cookie[]{accessTokenCookie});
//     }

//     @Test
//     void control() throws Exception {
//         given(deviceSettingAdapter.getPlaceList(anyString())).willReturn(List.of(PlaceResponse.builder().placeName("test place").placeCode("test place").build()));
//         given(deviceSettingAdapter.getPlace(anyString(), anyLong())).willReturn(PlaceResponse.builder().placeName("test place").placeCode("test place").build());
//         given(deviceSettingAdapter.getDeviceListByPlace(anyString(), anyLong())).willReturn(List.of(DeviceResponse.builder().deviceName("test device").aiMode(1).build()));
//         given(redisUtil.getDeviceStatus(DEVICE_KEY, "test place_test device")).willReturn(true);
//         given(redisUtil.getMode(anyString(), anyString())).willReturn(true);
//         given(jwtUtil.getAuthentication(anyString())).willReturn(new TestingAuthenticationToken("test", "test"));

//         mockMvc.perform(get("/control/1").cookie(new Cookie("accessToken", ACCESS_TOKEN))).andDo(print()).andExpect(status().isOk()).andExpect(handler().handlerType(ControlController.class)).andExpect(handler().methodName("control")).andExpect(model().attribute("status", Map.of("test device", true))).andExpect(model().attribute("placeList", List.of(PlaceResponse.builder().placeName("test place").placeCode("test place").build()))).andExpect(model().attribute("aiMode", true)).andExpect(model().attribute("custom", Map.of("test device", true))).andExpect(view().name("control"));
//     }

//     @Test
//     void light() throws Exception {
//         boolean isOn = true;
//         String place = "test place";
//         mockMvc.perform(get("/control/light").param("placeCode", place).param("isOn", Boolean.toString(isOn))).andDo(print()).andExpect(status().is3xxRedirection()).andExpect(handler().handlerType(ControlController.class)).andExpect(handler().methodName("light")).andExpect(redirectedUrl("/control"));
//     }

//     @Test
//     void airConditioner() throws Exception {
//         boolean isOn = true;
//         String place = "test place";
//         mockMvc.perform(get("/control/air-conditioner").param("placeCode", place).param("isOn", Boolean.toString(isOn))).andDo(print()).andExpect(status().is3xxRedirection()).andExpect(handler().handlerType(ControlController.class)).andExpect(handler().methodName("airConditioner")).andExpect(redirectedUrl("/control"));
//     }

//     @Test
//     void airCleaner() throws Exception {
//         boolean isOn = true;
//         String place = "test place";
//         mockMvc.perform(get("/control/air-cleaner").param("placeCode", place).param("isOn", Boolean.toString(isOn))).andDo(print()).andExpect(status().is3xxRedirection()).andExpect(handler().handlerType(ControlController.class)).andExpect(handler().methodName("airCleaner")).andExpect(redirectedUrl("/control"));
//     }

//     @Test
//     void aiMode() throws Exception {
//         boolean isOn = true;
//         String place = "test place";
//         mockMvc.perform(get("/control/ai-mode").param("placeCode", place).param("isOn", Boolean.toString(isOn))).andDo(print()).andExpect(status().is3xxRedirection()).andExpect(handler().handlerType(ControlController.class)).andExpect(handler().methodName("aiMode")).andExpect(redirectedUrl("/control"));
//     }

//     @Test
//     void customMode() throws Exception {
//         String deviceName = "test device";
//         String place = "test place";
//         boolean isOn = true;

//         mockMvc.perform(get("/control/custom-mode").param("deviceName", deviceName).param("placeCode", place).param("isOn", Boolean.toString(isOn))).andDo(print()).andExpect(status().is3xxRedirection()).andExpect(handler().handlerType(ControlController.class)).andExpect(handler().methodName("customMode")).andExpect(redirectedUrl("/control"));
//     }

//     @Test
//     void getAiResult() throws Exception {
//         String deviceName = "test device";
//         String time = "X시 X분";
//         String indoorTemperature = "23";
//         String indoorHumidity = "50";
//         String outdoorTemperature = "27";
//         String outdoorHumidity = "54";
//         String totalPeopleCount = "9";
//         String result = "ON";

//         given(redisUtil.getAiInfo(PREDICT_DATA, "deviceName")).willReturn(deviceName);
//         given(redisUtil.getAiInfo(PREDICT_DATA, "time")).willReturn(time);
//         given(redisUtil.getAiInfo(PREDICT_DATA, "indoorTemperature")).willReturn(indoorTemperature);
//         given(redisUtil.getAiInfo(PREDICT_DATA, "indoorHumidity")).willReturn(indoorHumidity);
//         given(redisUtil.getAiInfo(PREDICT_DATA, "outdoorTemperature")).willReturn(outdoorTemperature);
//         given(redisUtil.getAiInfo(PREDICT_DATA, "outdoorHumidity")).willReturn(outdoorHumidity);
//         given(redisUtil.getAiInfo(PREDICT_DATA, "totalPeopleCount")).willReturn(totalPeopleCount);
//         given(redisUtil.getAiInfo(PREDICT_DATA, "result")).willReturn(result);

//         mockMvc.perform(get("/control/ai-result"))
//                 .andDo(print())
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$['deviceName']", equalTo(deviceName)))
//                 .andExpect(jsonPath("$['time']", equalTo(time)))
//                 .andExpect(jsonPath("$['indoorTemperature']", equalTo(indoorTemperature)))
//                 .andExpect(jsonPath("$['indoorHumidity']", equalTo(indoorHumidity)))
//                 .andExpect(jsonPath("$['outdoorTemperature']", equalTo(outdoorTemperature)))
//                 .andExpect(jsonPath("$['outdoorHumidity']", equalTo(outdoorHumidity)))
//                 .andExpect(jsonPath("$['totalPeopleCount']", equalTo(totalPeopleCount)))
//                 .andExpect(jsonPath("$['result']", equalTo(result)));
//     }
// }
