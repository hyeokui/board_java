package com.example.demo.domain.domain.admin.service.registration.dto;

import com.example.demo.enums.admin.AdminStatus;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class AdminRegistrationServiceDto {

    @NotBlank
    private final String adminId;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private AdminStatus adminStatus;

    public AdminRegistrationServiceDto(String adminId, String password, String name, String email, AdminStatus adminStatus) {
        this.adminId = adminId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.adminStatus = adminStatus;
    }
}
