package com.nhnacademy.front.controller;

import com.nhnacademy.front.adaptor.UserAdaptor;
import com.nhnacademy.front.dto.UserRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserAdaptor userAdaptor;
    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("registerRequest", new UserRegisterRequest());
        return "register";
    }

    @PostMapping("/register")
    public String register(UserRegisterRequest userRegisterRequest){
        userAdaptor.createUser(userRegisterRequest);
        return "redirect:/login";
    }
}
