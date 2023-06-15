package com.example.demo.controller.core.user.login;

import com.example.demo.domain.domain.user.service.login.UserLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;


@Controller
@RequiredArgsConstructor
@RequestMapping("/user/login")
@SessionAttributes("userId")
public class UserLoginController {

    private final UserLoginService userLoginService;

    @GetMapping
    public String get(SessionStatus sessionStatus) {
        sessionStatus.setComplete();

        return "thymeleaf/user/login";
    }

    @PostMapping
    public String post(@RequestParam String userId, @RequestParam String password, Model model) {
        model.addAttribute("userId", userLoginService.login(userId, password));

        return "redirect:/main";
    }
}
