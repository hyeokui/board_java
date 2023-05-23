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
public class PermissionCheckServiceImpl implements PermissionCheckService {

    private final AdminRepository adminRepository;

    @Override
    public void checkBoardPermission(Long adminId) {
        Admin admin = checkExistsAdmin(adminId);

        if (admin.getAdminStatus() != AdminStatus.BOARD_ADMIN) {
            throw new InsufficientPermissionException();
        }
    }

    @Override
    public void checkPostPermission(Long adminId) {
        Admin admin = checkExistsAdmin(adminId);

        if (admin.getAdminStatus() != AdminStatus.POST_ADMIN) {
            throw new InsufficientPermissionException();
        }
    }

    @Override
    public void checkCommentPermission(Long adminId) {
        Admin admin = checkExistsAdmin(adminId);

        if (admin.getAdminStatus() != AdminStatus.COMMENT_ADMIN) {
            throw new InsufficientPermissionException();
        }
    }

    private Admin checkExistsAdmin(Long adminId) {

        return adminRepository.findById(adminId).orElseThrow(AdminNotFoundException::new);
    }

}
