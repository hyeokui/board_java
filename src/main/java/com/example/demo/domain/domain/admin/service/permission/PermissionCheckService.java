package com.example.demo.domain.domain.admin.service.permission;

public interface PermissionCheckService {

    void checkBoardPermission(Long adminId);
    void checkPostPermission(Long adminId);
    void checkCommentPermission(Long adminId);
}
