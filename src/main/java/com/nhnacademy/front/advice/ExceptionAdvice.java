package com.nhnacademy.front.advice;

import com.nhnacademy.front.adaptor.UserAdapter;
import com.nhnacademy.front.dto.AccessTokenResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@ControllerAdvice(basePackages = "com.nhnacademy.front")
@RequiredArgsConstructor
public class ExceptionAdvice {
    private final UserAdapter userAdapter;
    @ExceptionHandler(FeignException.class)
    public String feignExceptionHandler(FeignException exception, HttpServletRequest request, HttpServletResponse response) {
        System.out.println(exception.getMessage());
        if (exception.status() == 401) {
            try {
                Cookie refreshToken = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("refreshToken")).findFirst().orElse(null);

                AccessTokenResponse accesstokenresponse = userAdapter.reissue(refreshToken.getValue());
                Cookie accessCookie = new Cookie("accessToken", accesstokenresponse.getAccessToken());

                response.addCookie(accessCookie);
                return "redirect:" + request.getRequestURI();
            } catch (FeignException e) {
                return "redirect:/logout";
            }
        }

        return "redirect:/logout";
    }
}