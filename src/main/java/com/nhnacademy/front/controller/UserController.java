package com.nhnacademy.front.controller;

import com.nhnacademy.front.adaptor.UserAdapter;
import com.nhnacademy.front.dto.UserRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserAdapter userAdapter;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registerRequest", new UserRegisterRequest());
        return "register";
    }

    @PostMapping("/register")
    public String register(UserRegisterRequest userRegisterRequest, @RequestAttribute("_csrf") CsrfToken csrfToken) {
        userAdapter.createUser(userRegisterRequest, csrfToken.getToken());
        return "redirect:/login";
    }
}
