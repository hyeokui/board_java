package com.example.demo.domain.domain.user.service.registration.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
public class UserRegistrationServiceDto {
    @NotBlank
    private final String userId;

    @NotBlank
    private final String password;

    @NotBlank
    private final String confirmPassword;

    @NotBlank
    private final String name;

    @NotBlank
    private final String email;

    @NotBlank
    private final String phone;

    public UserRegistrationServiceDto(String userId, String password, String confirmPassword, String name, String email, String phone) {
        this.userId = userId;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}
