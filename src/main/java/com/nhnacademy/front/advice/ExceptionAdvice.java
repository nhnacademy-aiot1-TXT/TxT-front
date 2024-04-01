package com.nhnacademy.front.advice;

import com.nhnacademy.front.adaptor.UserAdapter;
import com.nhnacademy.front.dto.AccessTokenResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice(basePackages = "com.nhnacademy.front")
public class ExceptionAdvice {
    private final UserAdapter userAdapter;

    @ExceptionHandler(FeignException.class)
    public String feignExceptionHandler(FeignException exception, HttpServletRequest request, HttpServletResponse response) {
        if (exception.status() == 401) {
            try {
                log.info("cookies : {}", request);
                log.info("cookies : {}", request.getCookies());
                Cookie refreshToken = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("refreshToken")).findFirst().orElse(null);

                if (Objects.nonNull(refreshToken) && !"".equals(refreshToken.getValue())) {
                    AccessTokenResponse accesstokenresponse = userAdapter.reissue(refreshToken.getValue());
                    Cookie accessCookie = new Cookie("accessToken", accesstokenresponse.getAccessToken());
                    accessCookie.setHttpOnly(true);
                    accessCookie.setPath("/");

                    response.addCookie(accessCookie);
                }
                return "redirect:" + request.getRequestURI();
            } catch (FeignException e) {
                return "redirect:/logout";
            }
        }

        return "redirect:/logout";
    }
}