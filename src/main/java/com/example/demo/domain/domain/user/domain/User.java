package com.example.demo.domain.domain.user.domain;

import com.example.demo.enums.user.UserStatus;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    private UserStatus userStatus = UserStatus.ACTIVE;

    protected User() {
    }

    public User(String userId, String password, String name, String email, String phone) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public void update(String password, String name, String email, String phone) {
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public void delete(Long userId, String password) {
        this.userStatus = UserStatus.DELETED;
    }
}
