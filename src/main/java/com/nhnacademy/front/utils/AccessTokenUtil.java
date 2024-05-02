package com.nhnacademy.front.utils;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class AccessTokenUtil {
    private AccessTokenUtil(){}

    public static String findAccessTokenInRequest(HttpServletRequest request){
        Cookie cookie = Arrays.stream(request.getCookies())
                .filter(c -> "accessToken".equals(c.getName()))
                .findFirst()
                .orElse(null);

        if (cookie == null || cookie.getValue() == null){
            return "";
        }

        return cookie.getValue();
    }
}
