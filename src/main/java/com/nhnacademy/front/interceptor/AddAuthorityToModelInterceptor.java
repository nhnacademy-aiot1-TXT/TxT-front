package com.nhnacademy.front.interceptor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;

/**
 * Model에 authority 속성을 추가해주는 interceptor입니다.
 */
@Component
public class AddAuthorityToModelInterceptor implements HandlerInterceptor {
    /**
     * 쿠키에 저장된 access token payload를 디코딩하여 authority 값을 추출하고, model의 속성에 추가해줍니다.
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (modelAndView == null) {
            return;
        }
        if (request.getRequestURI().equals("/logout")) {
            return;
        }
        ModelMap model = modelAndView.getModelMap();
        if (Objects.isNull(request.getCookies())) {
            return;
        }
        Cookie cookie = Arrays.stream(request.getCookies())
                .filter(c -> "accessToken".equals(c.getName()))
                .findFirst()
                .orElse(null);
        if (cookie == null) {
            return;
        }

        String authority = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().orElse(null));
        model.addAttribute("authority", authority);
    }
}
