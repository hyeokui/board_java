package com.example.demo.domain.domain.admin.service.permission;

import com.example.demo.domain.domain.admin.domain.Admin;
import com.example.demo.domain.domain.admin.domain.AdminRepository;
import com.example.demo.enums.admin.AdminStatus;
import com.example.demo.exception.InsufficientPermissionException;
import com.example.demo.exception.user.AdminNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CheckCommentPermissionServiceImpl implements PermissionCheckService {

    private final AdminRepository adminRepository;

    @Override
    public void permissionCheck(String adminId) {
        Admin admin = checkExistsAdmin(adminId);

        if (admin.getAdminStatus() != AdminStatus.COMMENT_ADMIN) {
            throw new InsufficientPermissionException();
        }
    }

    private Admin checkExistsAdmin(String adminId) {

        return adminRepository.findOptionalAdminByAdminId(adminId).orElseThrow(AdminNotFoundException::new);
    }
}

