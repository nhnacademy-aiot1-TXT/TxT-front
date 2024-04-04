package com.nhnacademy.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 어드민 권한만 접근할 수 있는 Controller
 */
@RequestMapping("/admin")
@Controller
public class AdminController {
    @GetMapping
    public String admin() {
        return "redirect:/";
    }
}
