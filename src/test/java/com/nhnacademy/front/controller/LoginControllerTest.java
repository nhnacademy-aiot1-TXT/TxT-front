package com.nhnacademy.front.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.front.adaptor.UserAdapter;
import com.nhnacademy.front.dto.*;
import com.nhnacademy.front.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;

class LoginControllerTest {
    @Mock
    private UserAdapter userAdapter;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testLogin() throws JsonProcessingException {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setId("testUser");
        loginRequest.setPassword("testPassword");
        Model model = new ExtendedModelMap();
        Authentication authentication = new TestingAuthenticationToken("user", "password");

        UserDataResponse mockUserDataResponse = new UserDataResponse();
        mockUserDataResponse.setStatusName("ACTIVE");

        TokensResponse mockTokensResponse = new TokensResponse();
        AccessTokenResponse mockAccessTokenResponse = new AccessTokenResponse("testAccessToken", "test", 1);
        RefreshTokenResponse mockRefreshTokenResponse = new RefreshTokenResponse("testRefreshToken", 1);
        mockTokensResponse.setAccessToken(mockAccessTokenResponse);
        mockTokensResponse.setRefreshToken(mockRefreshTokenResponse);

        given(userAdapter.getUserData(anyString())).willReturn(mockUserDataResponse);
        given(userAdapter.doLogin(any(LoginRequest.class), anyString())).willReturn(mockTokensResponse);
        given(jwtUtil.getAuthentication(any(AccessTokenResponse.class))).willReturn(authentication);

        MockHttpServletResponse response = new MockHttpServletResponse();

        // When
        String result = loginController.login(loginRequest, response, new HttpSessionCsrfTokenRepository().generateToken(null), model);

        // Then
        assertEquals("redirect:/", result);

        // Assert cookies
        Cookie[] cookies = response.getCookies();
        assertEquals(2, cookies.length);
        assertEquals("accessToken", cookies[0].getName());
        assertEquals("testAccessToken", cookies[0].getValue());
        assertEquals("refreshToken", cookies[1].getName());
        assertEquals("testRefreshToken", cookies[1].getValue());
    }

    @Test
    void testLogin_exception() throws JsonProcessingException {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setId("testUser");
        loginRequest.setPassword("testPassword");
        MockHttpServletResponse response = new MockHttpServletResponse();
        Model model = new ExtendedModelMap();
        RuntimeException testException = new RuntimeException("test Exception");

        given(userAdapter.doLogin(any(LoginRequest.class), anyString())).willThrow(testException);

        // When
        String result = loginController.login(loginRequest, response, new HttpSessionCsrfTokenRepository().generateToken(null), model);

        // Then
        assertEquals("login", result);
    }
}