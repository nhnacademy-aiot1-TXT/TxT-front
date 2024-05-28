package com.nhnacademy.front.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.front.adaptor.UserAdapter;
import com.nhnacademy.front.dto.UserDataResponse;
import com.nhnacademy.front.dto.UserRegisterRequest;
import com.nhnacademy.front.dto.UserUpdateRequest;
import com.nhnacademy.front.utils.AccessTokenUtil;
import com.nhnacademy.front.utils.JsonResponseExceptionHandler;
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
     * @exception RuntimeException TxT-user-management 의 AlreadyExistEmailException 을 받아온다.
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
        return "redirect:/";
    }

    /**
     * 마이페이지를 구성하는 Controller
     *
     * @param request 사용자 요청 정보
     * @param model html 에서 thymeleaf 를 이용해서 쓸 model 설정
     * @return redirect 할 URL 문자열
     */
    @GetMapping("/user/profile")
    public String profile(HttpServletRequest request, Model model) {
        UserDataResponse user = userAdapter.getUserData(AccessTokenUtil.findAccessTokenInRequest(request));
        model.addAttribute("user", user);

        Optional<Cookie> updateSuccessCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> "isUpdateSuccess".equals(cookie.getName()))
                .findFirst();

        if (updateSuccessCookie.isPresent()){
            model.addAttribute("successMessage", "수정이 완료되었습니다.");
        }

        model.addAttribute("accessTokenTemp", AccessTokenUtil.findAccessTokenInRequest(request));

        return "profile";
    }

    /**
     * 마이페이지 정보 수정을 위한 Controller
     *
     * @param request 사용자 요청 정보
     * @param response 처리한 정보 응답
     * @param model html 에서 thymeleaf 를 이용해서 쓸 model 설정
     * @exception RuntimeException TxT-user-management 의 AlreadyExistEmailException 을 받아온다.
     * @return redirect 할 URL 문자열
     */
    @PostMapping("/user/profile")
    public String update(HttpServletRequest request, HttpServletResponse response, Model model) throws JsonProcessingException {
        try{
            String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);

            UserDataResponse user = userAdapter.getUserData(accessToken);
            model.addAttribute("user", user);

            userAdapter.updateUser(new UserUpdateRequest(request.getParameter("name"),
                    request.getParameter("password"),
                    request.getParameter("email")),
                    accessToken);

            Cookie cookie = new Cookie("isUpdateSuccess", "success");
            cookie.setMaxAge(5);
            response.addCookie(cookie);

        } catch (RuntimeException e){
            model.addAttribute("errorMessage", JsonResponseExceptionHandler.title(e));
            return "profile";
        }
        return "redirect:/user/profile";
    }

    @PostMapping("/user/profile/deactivate")
    public String deactivate(HttpServletRequest request, Model model) throws JsonProcessingException {
        try{
            String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
            userAdapter.deactivate(accessToken);
        } catch (RuntimeException e){
            model.addAttribute("errorMessage", JsonResponseExceptionHandler.title(e));
            return "profile";
        }
        return "redirect:/logout";
    }
}
