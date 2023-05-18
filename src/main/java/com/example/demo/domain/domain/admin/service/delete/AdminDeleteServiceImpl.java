package com.example.demo.domain.domain.admin.service.delete;

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
public class AdminDeleteServiceImpl implements AdminDeleteService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void delete(Long adminId, String password) {
        adminRepository.findById(adminId).ifPresentOrElse(admin -> {
                    checkPassword(adminId, password);
                    admin.delete(adminId, password);
                },
                () -> {
                    throw new AdminNotFoundException();
                }
        );
    }

    public void checkPassword(Long adminId, String password) {
        adminRepository.findById(adminId).ifPresent(admin -> {
                    if (bCryptPasswordEncoder.matches(password, admin.getPassword())) {
                        throw new InvalidPasswordException();
                    }
                }
        );
    }
}
