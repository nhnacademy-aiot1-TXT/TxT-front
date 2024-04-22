package com.nhnacademy.front.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.front.adaptor.UserAdapter;
import com.nhnacademy.front.dto.UserDataResponse;
import com.nhnacademy.front.dto.UserRegisterRequest;
import com.nhnacademy.front.dto.UserUpdateRequest;
import com.nhnacademy.front.util.JsonResponseExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

/**
 * 사용자 관련 기능을 담당하는 Controller 클래스
 */
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserAdapter userAdapter;

    /**
     * 회원가입을 처리하는 핸들러 메서드
     *
     * @param userRegisterRequest 사용자 등록 요청 정보
     * @param csrfToken           CSRF Token
     * @return redirect 할 URL 문자열
     */
    @PostMapping("/register")
    public String register(UserRegisterRequest userRegisterRequest, @RequestAttribute("_csrf") CsrfToken csrfToken, Model model) throws JsonProcessingException {
        try{
            userAdapter.createUser(userRegisterRequest, csrfToken.getToken());
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", JsonResponseExceptionHandler.title(e));
            if(!userRegisterRequest.getId().isEmpty() || !userRegisterRequest.getName().isEmpty() || !userRegisterRequest.getEmail().isEmpty()){
                model.addAttribute("userRegisterRequest", userRegisterRequest);
            }
            return "register";
        }
        return "redirect:/login";
    }

    @GetMapping("/user/profile")
    public String profile(HttpServletRequest request, Model model) {
        String accessToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "accessToken".equals(cookie.getName()))
                .findFirst()
                .orElse(null)
                .getValue();

        UserDataResponse user = userAdapter.getUserData(accessToken);
        model.addAttribute("user", user);

        Optional<Cookie> updateSuccessCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> "isUpdateSuccess".equals(cookie.getName()))
                .findFirst();

        if (updateSuccessCookie.isPresent()){
            model.addAttribute("successMessage", "수정이 완료되었습니다.");
        }

        return "profile";
    }

    @PostMapping("/user/profile")
    public String update(HttpServletRequest request, HttpServletResponse response, Model model) throws JsonProcessingException {
        try{
            String accessToken = Arrays.stream(request.getCookies())
                    .filter(cookie -> "accessToken".equals(cookie.getName()))
                    .findFirst()
                    .orElse(null)
                    .getValue();

            UserDataResponse user = userAdapter.getUserData(accessToken);
            model.addAttribute("user", user);

            userAdapter.updateUser(new UserUpdateRequest(request.getParameter("name"),
                    request.getParameter("password"),
                    request.getParameter("email")),
                    accessToken);

            Cookie cookie = new Cookie("isUpdateSuccess", "success");
            cookie.setMaxAge(60);
            response.addCookie(cookie);

        } catch (RuntimeException e){
            model.addAttribute("errorMessage", JsonResponseExceptionHandler.title(e));
            return "profile";
        }
        return "redirect:/user/profile";
    }
}
