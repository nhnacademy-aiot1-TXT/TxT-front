package com.nhnacademy.front.utils;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 액세스 토큰 관련 작업을 처리하기 위한 유틸 클래스
 *
 * @author parksangwon
 * @version 1.0.0
 */
public class AccessTokenUtil {
    private AccessTokenUtil() {
    }

    /**
     * 요청에서 accessToken을 추출하는 메서드
     *
     * @param request the request
     * @return the string
     */
    public static String findAccessTokenInRequest(HttpServletRequest request) {
        Cookie cookie = Arrays.stream(request.getCookies())
                .filter(c -> "accessToken".equals(c.getName()))
                .findFirst()
                .orElse(null);

        if (cookie == null || cookie.getValue() == null) {
            return "";
        }

        return cookie.getValue();
    }
}
