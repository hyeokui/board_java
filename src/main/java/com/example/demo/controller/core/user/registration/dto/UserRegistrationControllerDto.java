package com.example.demo.controller.core.user.registration.dto;

import com.example.demo.domain.domain.user.service.registration.dto.UserRegistrationServiceDto;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserRegistrationControllerDto {

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

    public UserRegistrationControllerDto(String userId, String password, String confirmPassword, String name, String email, String phone) {
        this.userId = userId;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public UserRegistrationServiceDto convertServiceDto() {
        return new UserRegistrationServiceDto(this.userId, this.password, this.confirmPassword, this.name, this.email, this.phone);
    }

}
