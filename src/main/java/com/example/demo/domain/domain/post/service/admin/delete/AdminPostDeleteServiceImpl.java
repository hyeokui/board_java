package com.example.demo.domain.domain.post.service.admin.delete;

import com.example.demo.domain.domain.admin.domain.Admin;
import com.example.demo.domain.domain.admin.domain.AdminRepository;
import com.example.demo.domain.domain.admin.service.permission.PermissionCheckService;
import com.example.demo.domain.domain.board.service.status.OperatingStatusService;
import com.example.demo.domain.domain.post.domain.PostRepository;
import com.example.demo.domain.domain.user.domain.User;
import com.example.demo.enums.post.PostStatus;
import com.example.demo.exception.admin.AdminNotFoundException;
import com.example.demo.exception.post.PostNotFoundException;
import com.example.demo.exception.user.InvalidPasswordException;
import com.example.demo.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminPostDeleteServiceImpl implements AdminPostDeleteService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AdminRepository adminRepository;
    private final PostRepository postRepository;
    private final PermissionCheckService permissionCheckService;
    private final OperatingStatusService operatingStatusService;

    @Override
    public void delete(Long postId, Long adminId, String password, String boardName) {
        postRepository.findByIdAndAdminIdAndPostStatus(postId, adminId, PostStatus.ACTIVE).ifPresentOrElse(post -> {
                    permissionCheckService.checkPostPermission(adminId);
                    operatingStatusService.isBoardOperating(boardName);
                    foundIdAndCheckPassword(adminId, password);
                    post.delete();
                },
                () -> {
                    throw new PostNotFoundException();
                }
        );
    }

    private void foundIdAndCheckPassword(Long adminId, String password) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(AdminNotFoundException::new);

        if (!bCryptPasswordEncoder.matches(password, admin.getPassword())) {
            throw new InvalidPasswordException();
        }
    }
}
