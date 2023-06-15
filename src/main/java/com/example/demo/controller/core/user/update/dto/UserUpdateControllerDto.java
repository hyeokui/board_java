package com.example.demo.controller.core.user.update.dto;

import com.example.demo.domain.domain.user.service.update.dto.UserUpdateServiceDto;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
public class UserUpdateControllerDto {
    @NotNull(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotNull(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotNull(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @NotNull(message = "전화번호는 필수 입력 값입니다.")
    private String phone;

    public UserUpdateControllerDto(String name, String password, String email, String phone) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public UserUpdateServiceDto convertServiceDto() {
        return new UserUpdateServiceDto(this.name, this.password, this.email, this.phone);
    }
}
