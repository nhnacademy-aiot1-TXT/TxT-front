package com.nhnacademy.front.controller;

import com.nhnacademy.front.adaptor.UserAdaptor;
import com.nhnacademy.front.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class LogoutController {
    private final UserAdaptor userAdaptor;
    private final RedisUtil redisUtil;

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie accessCookie = Arrays.stream(request.getCookies())
                .filter(c -> "accessToken".equals(c.getName()))
                .findFirst()
                .orElse(new Cookie("accessToken", ""));
        Cookie refreshCookie = Arrays.stream(request.getCookies())
                .filter(c -> "refreshCookie".equals(c.getName()))
                .findFirst()
                .orElse(new Cookie("refreshCookie", ""));

        accessCookie.setMaxAge(0);
        accessCookie.setPath("/");
        response.addCookie(accessCookie);
        refreshCookie.setMaxAge(0);
        refreshCookie.setPath("/");
        response.addCookie(refreshCookie);

        if (Objects.nonNull(accessCookie.getValue()) && !"".equals(accessCookie.getValue())) {
            redisUtil.setBlackList(accessCookie.getValue(), "access_token", (long) (1000 * 60 * 60));
        }

        userAdaptor.doLogout(refreshCookie.getValue());

        return "redirect:/login";
    }
}
