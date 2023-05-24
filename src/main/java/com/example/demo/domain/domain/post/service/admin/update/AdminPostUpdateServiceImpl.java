package com.example.demo.domain.domain.post.service.admin.update;

import com.example.demo.domain.domain.admin.service.permission.PermissionCheckService;
import com.example.demo.domain.domain.post.domain.PostRepository;
import com.example.demo.exception.admin.AdminNotFoundException;
import com.example.demo.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminPostUpdateServiceImpl implements AdminPostUpdateService {

    private final PostRepository postRepository;
    private final PermissionCheckService permissionCheckService;

    @Override
    public void update(Long adminId, String title, String content) {
        postRepository.findByAdminId(adminId).ifPresentOrElse(post -> {
                    permissionCheckService.checkPostPermission(adminId);
                    post.update(title, content);
                },
                () -> {
                    throw new AdminNotFoundException();
                }
        );
    }
}
