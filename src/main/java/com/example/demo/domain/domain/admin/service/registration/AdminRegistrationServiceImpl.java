package com.example.demo.domain.domain.admin.service.registration;

import com.example.demo.domain.domain.admin.domain.Admin;
import com.example.demo.domain.domain.admin.domain.AdminRepository;
import com.example.demo.domain.domain.admin.service.registration.dto.AdminRegistrationServiceDto;
import com.example.demo.exception.admin.DuplicateAdminIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminRegistrationServiceImpl implements AdminRegistrationService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void add(AdminRegistrationServiceDto adminRegistrationServiceDto) {
        Admin admin = new Admin(adminRegistrationServiceDto.getAdminId(),
                bCryptPasswordEncoder.encode(adminRegistrationServiceDto.getPassword()),
                adminRegistrationServiceDto.getName(),
                adminRegistrationServiceDto.getEmail(),
                adminRegistrationServiceDto.getAdminStatus());

        checkDuplicationAdminId(adminRegistrationServiceDto);

        adminRepository.save(admin);
    }

    private void checkDuplicationAdminId(AdminRegistrationServiceDto adminRegistrationServiceDto) {
        if (adminRepository.existsByAdminId(adminRegistrationServiceDto.getAdminId())) {
            throw new DuplicateAdminIdException(adminRegistrationServiceDto.getAdminId() + "is already exists");
        }
    }
}
