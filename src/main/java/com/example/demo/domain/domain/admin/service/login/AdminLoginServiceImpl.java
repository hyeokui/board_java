package com.example.demo.domain.domain.admin.service.login;

import com.example.demo.domain.domain.admin.domain.Admin;
import com.example.demo.domain.domain.admin.domain.AdminRepository;
import com.example.demo.exception.user.AdminNotFoundException;
import com.example.demo.exception.user.InvalidPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class AdminLoginServiceImpl implements AdminLoginService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Long login(String adminId, String password) {

        return foundIdAndCheckPassword(adminId, password).getId();
    }

    private Admin foundIdAndCheckPassword(String adminId, String password) {
        Admin admin = adminRepository.findOptionalAdminByAdminId(adminId).orElseThrow(AdminNotFoundException::new);

        if (!bCryptPasswordEncoder.matches(password, admin.getPassword())) {
            throw new InvalidPasswordException();
        }

        return admin;
    }
}
