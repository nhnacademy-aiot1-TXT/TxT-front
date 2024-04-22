package com.nhnacademy.front.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;

/**
 * 로그인 시, auth/login, auth/register 등 특정 url 을 접근하지 못하도록 하는 인터셉터.
 */
@Component
public class LoggedInUserAccessInterceptor implements HandlerInterceptor {

    /**
     * 요청이 처리되기 전에 호출되는 메서드로, 로그인한 사용자의 액세스를 제어.
     *
     * @param request  HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @param handler  요청을 처리할 핸들러 객체
     * @return 요청 처리를 계속할지 여부를 나타내는 boolean 값
     * @throws Exception 예외 발생 시
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie accessToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "accessToken".equals(cookie.getName()))
                .findFirst()
                .orElse(null);
        if (Objects.nonNull(accessToken)) {
            response.sendRedirect(request.getContextPath() + "/");
            return false;
        }
        return true;
    }
}
