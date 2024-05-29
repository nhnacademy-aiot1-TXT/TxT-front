package com.nhnacademy.front.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.front.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * Model에 authority 속성을 추가해주는 interceptor입니다.
 */
@Component
@RequiredArgsConstructor
public class AddAuthorityToModelInterceptor implements HandlerInterceptor {
    private final JwtUtil jwtUtil;

    /**
     * 쿠키에 저장된 access token payload를 디코딩하여 authority 값을 추출하고, model의 속성에 추가해줍니다.
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws IOException {
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

        try {
            Authentication authentication = jwtUtil.getAuthentication(cookie.getValue());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (RuntimeException | JsonProcessingException e) {
            String errorMessage = "";
            if (e.toString().contains("401")) {
                errorMessage = "아이디 또는 비밀번호가 일치하지 않습니다.";
            }
            model.addAttribute("errorMessage", errorMessage);
            response.sendRedirect("login");
        }

        String authority = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().orElse(null));
        model.addAttribute("authority", authority);
    }
}
