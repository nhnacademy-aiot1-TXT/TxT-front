package com.nhnacademy.front.controller;

import com.nhnacademy.front.annotation.AddAuthorityToModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    @AddAuthorityToModel
    public String index(Model model) {
        return "index";
    }
}
