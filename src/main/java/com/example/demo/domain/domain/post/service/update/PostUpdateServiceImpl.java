package com.example.demo.domain.domain.post.service.update;

import com.example.demo.domain.domain.admin.service.permission.PermissionCheckService;
import com.example.demo.domain.domain.post.domain.PostRepository;
import com.example.demo.exception.user.AdminNotFoundException;
import com.example.demo.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostUpdateServiceImpl implements PostUpdateService {

    private final PostRepository postRepository;
    private final PermissionCheckService permissionCheckService;

    @Override
    public void updateByAdmin(Long adminId, String title, String content) {
        postRepository.findByAdminId(adminId).ifPresentOrElse(post -> {
                    permissionCheckService.checkPostPermission(adminId);
                    post.update(title, content);
                },
                () -> {
                    throw new AdminNotFoundException();
                }
        );
    }

    @Override
    public void updateByUser(Long userId, String title, String content) {
        postRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new)
                .update(title, content);
    }
}
