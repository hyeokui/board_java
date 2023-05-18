package com.example.demo.domain.domain.admin.domain;

import com.example.demo.enums.admin.AdminStatus;
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

    @NotBlank
    @Column(nullable = false, unique = true)
    private String adminId;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AdminStatus adminStatus;

    public Admin(String adminId, String password, String name, String email, AdminStatus adminStatus) {
        this.adminId = adminId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.adminStatus = adminStatus;
    }

    public void update(String password, String name, String email) {
        this.password = password;
        this.name = name;
        this.email = email;

    }

    public void delete(Long adminId, String password) {
        this.adminStatus = AdminStatus.DELETE;
    }
}
