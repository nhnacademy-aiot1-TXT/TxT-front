package com.nhnacademy.front.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Base64;

@Aspect
@Component
public class AuthorityAspect {

    @Before(value = "@annotation(com.nhnacademy.front.annotation.AddAuthorityToModel) && (args(model) || args(model, ..))")
    public void addAuthorityToModel(Model model) {

        if (model == null) {
            return ;
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Cookie cookie = Arrays.stream(request.getCookies())
                              .filter(c -> "accessToken".equals(c.getName()))
                              .findFirst()
                              .orElse(null);

        if (cookie == null) {
            return;
        }

        String payload = new String(Base64.getUrlDecoder().decode(cookie.getValue().split("\\.")[1]));
        String authority = new JSONObject(payload).getString("authority");
        model.addAttribute("authority", authority);
    }
}
