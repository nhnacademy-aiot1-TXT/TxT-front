package com.nhnacademy.front.controller;

import com.nhnacademy.front.adaptor.UserAdaptor;
import com.nhnacademy.front.dto.AccessTokenResponse;
import com.nhnacademy.front.dto.LoginRequest;
import com.nhnacademy.front.dto.RefreshTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserAdaptor userAdaptor;
    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public String login(LoginRequest loginRequest, HttpServletResponse response){
        Map<String, Object> tokens = userAdaptor.doLogin(loginRequest);

        AccessTokenResponse accessTokenResponse = (AccessTokenResponse) tokens.get("accessToken");
        RefreshTokenResponse refreshTokenResponse = (RefreshTokenResponse) tokens.get("refreshToken");

        Cookie accessCookie = new Cookie("accessToken", accessTokenResponse.getAccessToken());
        Cookie refreshCookie = new Cookie("refreshToken", refreshTokenResponse.getRefreshToken());

        accessCookie.setHttpOnly(true);
        refreshCookie.setHttpOnly(true);

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);

        return "redirect:/";
    }
}
