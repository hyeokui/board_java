package com.example.demo.controller.core.user.registration;

import com.example.demo.controller.core.user.registration.dto.UserRegistrationControllerDto;
import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.domain.domain.user.service.registration.UserRegistrationService;
import com.example.demo.domain.domain.user.service.registration.dto.UserRegistrationServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/registration")
public class UserRegistrationController {

    private final UserRepository userRepository;
    private final UserRegistrationService userRegistrationService;

    @GetMapping
    public String get() {

        return "thymeleaf/user/registration";
    }

    @PostMapping
    public String post(@Validated UserRegistrationControllerDto userRegistrationControllerDto) {

        userRegistrationService.add(new UserRegistrationServiceDto(
                        userRegistrationControllerDto.getUserId(),
                        userRegistrationControllerDto.getPassword(),
                        userRegistrationControllerDto.getConfirmPassword(),
                        userRegistrationControllerDto.getName(),
                        userRegistrationControllerDto.getEmail(),
                        userRegistrationControllerDto.getPhone()
                )
        );

        return "redirect:/user/login";
    }
}
