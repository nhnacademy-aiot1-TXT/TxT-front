package com.nhnacademy.front.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (Objects.isNull(request.getCookies())) {
            return false;
        }
        Cookie accessToken = Arrays.stream(request.getCookies()).filter(cookie -> "accessToken".equals(cookie.getName())).findFirst().orElse(null);
        if (Objects.isNull(accessToken)) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return false;
        }
        return true;
    }
}