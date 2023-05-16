package com.example.demo.domain.domain.admin.domain;

import com.example.demo.enums.user.AdminStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(nullable = false, unique = true)
    private String adminId;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String password;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotNull
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    private AdminStatus adminStatus = AdminStatus.ACTIVE;

    public Admin(String adminId, String password, String name, String email) {
        this.adminId = adminId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public void update(String password, String name, String email) {
        this.password = password;
        this.name = name;
        this.email = email;

    }

    public void delete(Long adminId) {
        this.adminStatus = AdminStatus.DELETE;
    }
}
