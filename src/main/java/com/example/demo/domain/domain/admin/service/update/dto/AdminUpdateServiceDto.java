package com.example.demo.domain.domain.admin.service.update.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class AdminUpdateServiceDto {

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String email;

    public AdminUpdateServiceDto(String password, String name, String email) {
        this.password = password;
        this.name = name;
        this.email = email;
    }
}
