package com.example.demo.controller.core.user.mypage;

import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/my-page")
public class UserMyPageController {

    private final UserRepository userRepository;

    @GetMapping
    public String get(@SessionAttribute Long userId, Model model) {
        model.addAttribute("user", userRepository.findById(userId).orElseThrow(UserNotFoundException::new));

        return "thymeleaf/user/my-page";
    }
}
