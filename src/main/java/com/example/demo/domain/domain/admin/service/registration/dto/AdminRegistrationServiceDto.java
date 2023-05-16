package com.example.demo.domain.domain.admin.service.registration.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class AdminRegistrationServiceDto {

    @NotNull
    @NotBlank
    private final String adminId;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String email;

    public AdminRegistrationServiceDto(String adminId, String password, String name, String email) {
        this.adminId = adminId;
        this.password = password;
        this.name = name;
        this.email = email;
    }
}
