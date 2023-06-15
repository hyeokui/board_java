package com.example.demo.controller.core.user.update;

import com.example.demo.controller.core.user.update.dto.UserUpdateControllerDto;
import com.example.demo.domain.domain.user.domain.User;
import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.domain.domain.user.service.update.UserUpdateService;
import com.example.demo.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/update")
public class UserUpdateController {

    private final UserRepository userRepository;
    private final UserUpdateService userUpdateService;

    @GetMapping
    public String get(@SessionAttribute Long userId, Model model) {
        model.addAttribute("user", userRepository.findById(userId).orElseThrow(UserNotFoundException::new));

        return "thymeleaf/user/update";
    }

    @PostMapping
    public String post(@SessionAttribute Long userId, @Valid UserUpdateControllerDto userUpdateControllerDto) {
        userUpdateService.update(userId, userUpdateControllerDto.convertServiceDto());

        return "redirect:/user/my-page";
    }
}
