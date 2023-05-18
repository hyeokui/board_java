package com.example.demo.domain.domain.post.service.write;

import com.example.demo.domain.domain.admin.domain.Admin;
import com.example.demo.domain.domain.admin.domain.AdminRepository;
import com.example.demo.domain.domain.admin.service.permission.CheckBoardPermissionServiceImpl;
import com.example.demo.domain.domain.admin.service.permission.PermissionCheckService;
import com.example.demo.domain.domain.board.domain.Board;
import com.example.demo.domain.domain.board.domain.BoardRepository;
import com.example.demo.domain.domain.post.domain.Post;
import com.example.demo.domain.domain.post.domain.PostRepository;
import com.example.demo.enums.admin.AdminStatus;
import com.example.demo.exception.InsufficientPermissionException;
import com.example.demo.exception.user.AdminNotFoundException;
import com.example.demo.exception.user.BoardNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostWriteServiceImpl implements PostWriteService {

    private final AdminRepository adminRepository;
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final CheckBoardPermissionServiceImpl checkBoardPermissionService;

    @Override
    public void write(String adminId, String title, String content, String boardName) {
        checkBoardPermissionService.permissionCheck(adminId);
        adminRepository.findOptionalAdminByAdminId(adminId).ifPresent(admin -> {
                    Post post = new Post(title, content, admin, board(boardName));
                    postRepository.save(post);
                }
        );
    }

    private Board board(String boardName) {
        return boardRepository.findByName(boardName).orElseThrow(BoardNotFoundException::new);
    }
}
