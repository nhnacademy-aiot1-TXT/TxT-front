package com.nhnacademy.front.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;

@Component
public class LoggedInUserAccessInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie accessToken = Arrays.stream(request.getCookies()).filter(cookie -> "accessToken".equals(cookie.getName())).findFirst().orElse(null);
        if (Objects.nonNull(accessToken)) {
            response.sendRedirect(request.getContextPath() + "/");
            return false;
        }
        return true;
    }
}
