package com.example.demo.domain.domain.admin.service.update;

import com.example.demo.domain.domain.admin.domain.AdminRepository;
import com.example.demo.domain.domain.admin.service.update.dto.AdminUpdateServiceDto;
import com.example.demo.exception.user.AdminNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminUpdateServiceImpl implements AdminUpdateService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void update(Long adminId, AdminUpdateServiceDto adminUpdateServiceDto) {
        adminRepository.findById(adminId)
                .orElseThrow(AdminNotFoundException::new)
                .update(bCryptPasswordEncoder.encode(adminUpdateServiceDto.getPassword()),
                        adminUpdateServiceDto.getName(),
                        adminUpdateServiceDto.getEmail()
                );
    }
}
