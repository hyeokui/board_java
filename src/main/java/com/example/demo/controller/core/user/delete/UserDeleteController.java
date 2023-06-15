package com.example.demo.controller.core.user.delete;

import com.example.demo.domain.domain.user.service.delete.UserDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/delete")
public class UserDeleteController {

    private final UserDeleteService userDeleteService;

    @GetMapping
    public String get() {

        return "thymeleaf/user/delete";
    }

    @PostMapping
    public String post(@SessionAttribute Long userId, @RequestParam String password) {
        userDeleteService.delete(userId, password);

        return "redirect:/user/login";

    }
}
