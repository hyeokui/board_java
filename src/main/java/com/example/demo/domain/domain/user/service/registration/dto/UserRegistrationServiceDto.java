package com.example.demo.domain.domain.user.service.registration.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
public class UserRegistrationServiceDto {

    @NotNull(message = "아이디는 필수 입력 값입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,12}$\n", message = "아이디는 영어와 숫자로 사용 가능하며 6자리 이상 12자리 이하입니다.")
    private final String userId;

    @NotNull(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 영어,숫자,특수문자 사용 가능하며 8자리 이상 16자리 이하입니다.")
    private final String password;

    @NotNull(message = "이름은 필수 입력 값입니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "이름은 특수문자를 제외한 2자리 이상 10자리 이하입니다.")
    private final String name;

    @NotNull(message = "이메일은 필수 입력 값입니다.")
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    private final String email;

    @NotNull(message = "전화번호는 필수 입력 값입니다.")
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "전화번호는 000-0000-0000 형식으로 입력해주세요.")
    private final String phone;

    public UserRegistrationServiceDto(String userId, String password, String name, String email, String phone) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}
