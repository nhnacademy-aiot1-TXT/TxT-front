package com.nhnacademy.front.controller;

import com.nhnacademy.front.adaptor.UserAdapter;
import com.nhnacademy.front.dto.AccessTokenResponse;
import com.nhnacademy.front.dto.LoginRequest;
import com.nhnacademy.front.dto.RefreshTokenResponse;
import com.nhnacademy.front.dto.TokensResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 로그인 처리 Controller 클래스
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserAdapter userAdapter;

    /**
     * 로그인 폼을 보여주는 핸들러 메서드
     * @param model Model 객체
     * @return 로그인 폼 뷰 이름
     */
    @GetMapping("/login")
    public String loginForm(HttpServletRequest req, Model model) {
        System.out.println("request port: " + req.getServerPort());
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    /**
     * 로그인 처리 핸들러 메서드
     * @param loginRequest 로그인 요청 정보
     * @param response HTTP 응답 객체
     * @param csrfToken CSRF 토큰
     * @return redirect 할 URL 문자열
     */
    @PostMapping("/login")
    public String login(LoginRequest loginRequest, HttpServletResponse response, @RequestAttribute("_csrf") CsrfToken csrfToken) {
        TokensResponse tokens = userAdapter.doLogin(loginRequest, csrfToken.getToken());

        AccessTokenResponse accessTokenResponse = tokens.getAccessToken();
        RefreshTokenResponse refreshTokenResponse = tokens.getRefreshToken();

        Cookie accessCookie = new Cookie("accessToken", accessTokenResponse.getAccessToken());
        Cookie refreshCookie = new Cookie("refreshToken", refreshTokenResponse.getRefreshToken());

        accessCookie.setHttpOnly(true);
        refreshCookie.setHttpOnly(true);

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);

        return "redirect:/";
    }
}
