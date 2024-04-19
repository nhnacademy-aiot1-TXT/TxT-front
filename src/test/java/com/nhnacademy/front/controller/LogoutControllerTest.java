package com.nhnacademy.front.controller;

import com.nhnacademy.front.adaptor.UserAdapter;
import com.nhnacademy.front.utils.RedisUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.servlet.http.Cookie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

class LogoutControllerTest {
    @Mock
    private UserAdapter userAdapter;

    @Mock
    private RedisUtil redisUtil;

    @InjectMocks
    private LogoutController logoutController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLogout() {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();


        Cookie accessCookie = new Cookie("accessToken", "testAccessToken");
        Cookie refreshCookie = new Cookie("refreshToken", "testRefreshToken");
        request.setCookies(accessCookie, refreshCookie);

        CsrfToken csrfToken = new HttpSessionCsrfTokenRepository().generateToken(null);

        // When
        String result = logoutController.logout(request, response, csrfToken);

        // Then
        assertEquals("redirect:/", result);

        // Verify cookies are deleted
        assertEquals(response.getCookie("accessToken").getMaxAge(), 0);
        assertEquals(response.getCookie("refreshToken").getMaxAge(), 0);

        // Verify userAdapter.doLogout() and redisUtil.setBlackList() are called
        verify(userAdapter).doLogout(eq("testRefreshToken"), eq(csrfToken.getToken()));
        verify(redisUtil).setBlackList(eq("testAccessToken"), eq("access_token"), eq(3600000L));
    }
}