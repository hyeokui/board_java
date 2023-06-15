package com.example.demo.controller.core.user.main;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/main")
public class Main {

    @GetMapping
    public String get() {

        return "thymeleaf/main-page";
    }
}
