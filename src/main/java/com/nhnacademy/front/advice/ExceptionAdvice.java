package com.nhnacademy.front.advice;

import com.nhnacademy.front.adaptor.UserAdapter;
import com.nhnacademy.front.dto.AccessTokenResponse;
import com.nhnacademy.front.error.DeviceRegisterException;
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

/**
 * 전역적으로 예외를 처리하는 Controller Advice 클래스
 */
@Slf4j
@RequiredArgsConstructor
@ControllerAdvice(basePackages = "com.nhnacademy.front")
public class ExceptionAdvice {
    private final UserAdapter userAdapter;

    /**
     * FeignException 예외 처리 메서드
     *
     * @param exception FeignException 객체
     * @param request   HTTP 요청 객체
     * @param response  HTTP 응답 객체
     * @return redirect 할 URL 문자열
     */
    @ExceptionHandler(FeignException.class)
    public String feignExceptionHandler(FeignException exception, HttpServletRequest request, HttpServletResponse response) {
        if (exception.status() == 401) {
            try {
                Cookie refreshToken = Arrays.stream(request.getCookies())
                                            .filter(cookie -> cookie.getName().equals("refreshToken"))
                                            .findFirst()
                                            .orElse(null);

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

        if (exception.status() == 403) {
            return "redirect:/";
        }

        return "redirect:/error";
    }

    @ExceptionHandler(DeviceRegisterException.class)
    public String exceptionHandler(DeviceRegisterException exception) {
        log.error(exception.getMessage(), exception);
        return "redirect:/admin/device/register";
    }
}
