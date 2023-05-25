package com.example.demo.domain.domain.post.service.admin.update;

import com.example.demo.domain.domain.admin.service.permission.PermissionCheckService;
import com.example.demo.domain.domain.board.domain.BoardRepository;
import com.example.demo.domain.domain.board.service.status.OperatingStatusService;
import com.example.demo.domain.domain.post.domain.PostRepository;
import com.example.demo.enums.board.BoardStatus;
import com.example.demo.enums.post.PostStatus;
import com.example.demo.exception.admin.AdminNotFoundException;
import com.example.demo.exception.board.BoardNotFoundException;
import com.example.demo.exception.board.BoardNotOperatingException;
import com.example.demo.exception.post.PostNotFoundException;
import com.example.demo.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminPostUpdateServiceImpl implements AdminPostUpdateService {

    private final PostRepository postRepository;
    private final OperatingStatusService operatingStatusService;
    private final PermissionCheckService permissionCheckService;

    @Override
    public void update(Long postId, Long adminId, String title, String content, String boardName) {
        postRepository.findByIdAndAdminIdAndPostStatus(postId, adminId, PostStatus.ACTIVE).ifPresentOrElse(post -> {
                    permissionCheckService.checkPostPermission(adminId);
                    operatingStatusService.isBoardOperating(boardName);
                    post.update(title, content);
                },
                () -> {
                    throw new PostNotFoundException();
                }
        );
    }
}