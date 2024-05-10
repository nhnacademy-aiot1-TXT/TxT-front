package com.nhnacademy.front.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.front.adaptor.UserAdapter;
import com.nhnacademy.front.dto.LoginRequest;
import com.nhnacademy.front.dto.UserDataResponse;
import com.nhnacademy.front.dto.UserRegisterRequest;
import com.nhnacademy.front.dto.UserUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;

class UserControllerTest {
    @Mock
    private UserAdapter userAdapter;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void register() throws JsonProcessingException {
        // Given
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setId("testUser");
        userRegisterRequest.setPassword("testPassword");
        Model model = new ExtendedModelMap();

        // When
        String result = userController.register(userRegisterRequest, new HttpSessionCsrfTokenRepository().generateToken(null), model);

        // Then
        assertEquals("redirect:/", result);
    }

    @Test
    void register_exception() throws JsonProcessingException {
        // Given
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setId("testUser");
        userRegisterRequest.setPassword("testPassword");
        Model model = new ExtendedModelMap();
        RuntimeException testException = new RuntimeException("{\"title\":\"Test Error Message\"}");

        willThrow(testException).given(userAdapter).createUser(any(UserRegisterRequest.class), any(String.class));

        // When
        String result = userController.register(userRegisterRequest, new HttpSessionCsrfTokenRepository().generateToken(null), model);

        // Then
        assertEquals("register", result);
    }

    @Test
    void profile() {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        Model model = new ExtendedModelMap();

        // mock accessToken 쿠키
        Cookie accessTokenCookie = new Cookie("accessToken", "testAccessToken");
        request.setCookies(accessTokenCookie);

        UserDataResponse mockUserDataResponse = new UserDataResponse();
        mockUserDataResponse.setId("testUser");
        mockUserDataResponse.setEmail("test@example.com");

        given(userAdapter.getUserData(anyString())).willReturn(mockUserDataResponse);

        // When
        String result = userController.profile(request, model);

        // Then
        UserDataResponse user = (UserDataResponse) model.getAttribute("user");

        assertEquals("profile", result);
        assertEquals("testUser", user.getId());
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void update() throws JsonProcessingException {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Model model = new ExtendedModelMap();

        // mock accessToken 쿠키
        Cookie accessTokenCookie = new Cookie("accessToken", "testAccessToken");
        request.setCookies(accessTokenCookie);

        UserDataResponse mockUserDataResponse = new UserDataResponse();
        mockUserDataResponse.setId("testUser");
        mockUserDataResponse.setEmail("test@example.com");

        given(userAdapter.getUserData(anyString())).willReturn(mockUserDataResponse);

        // When
        String result = userController.update(request, response, model);

        //Then
        UserDataResponse user = (UserDataResponse) model.getAttribute("user");

        assertEquals("redirect:/user/profile", result);
        assertEquals("testUser", user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("isUpdateSuccess", response.getCookie("isUpdateSuccess").getName());
        assertEquals("success", response.getCookie("isUpdateSuccess").getValue());
        verify(userAdapter, times(1)).updateUser(any(UserUpdateRequest.class), anyString());
    }

    @Test
    void update_exception() throws JsonProcessingException {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Model model = new ExtendedModelMap();
        RuntimeException testException = new RuntimeException("{\"title\":\"Test Error Message\"}");

        // mock accessToken 쿠키
        Cookie accessTokenCookie = new Cookie("accessToken", "testAccessToken");
        request.setCookies(accessTokenCookie);

        given(userAdapter.getUserData(anyString())).willReturn(new UserDataResponse());
        willThrow(testException).given(userAdapter).updateUser(any(UserUpdateRequest.class), anyString());

        // When
        String result = userController.update(request, response, model);

        // Then
        assertEquals("profile", result);

    }
}