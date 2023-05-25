package com.example.demo.domain.domain.post.service.admin.write;

import com.example.demo.domain.domain.admin.domain.AdminRepository;
import com.example.demo.domain.domain.admin.service.permission.PermissionCheckService;
import com.example.demo.domain.domain.board.domain.Board;
import com.example.demo.domain.domain.board.domain.BoardRepository;
import com.example.demo.domain.domain.board.service.status.OperatingStatusService;
import com.example.demo.domain.domain.post.domain.Post;
import com.example.demo.domain.domain.post.domain.PostRepository;
import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.enums.board.BoardStatus;
import com.example.demo.exception.board.BoardNotFoundException;
import com.example.demo.exception.board.BoardNotOperatingException;
import com.example.demo.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminPostWriteServiceImpl implements AdminPostWriteService {

    private final AdminRepository adminRepository;
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final OperatingStatusService operatingStatusService;
    private final PermissionCheckService permissionCheckService;

    @Override
    public void write(Long adminId, String title, String content, String boardName) {
        adminRepository.findById(adminId).ifPresent(admin -> {
                    permissionCheckService.checkPostPermission(adminId);
                    operatingStatusService.isBoardOperating(boardName);

                    Post post = new Post(title, content, admin, board(boardName));
                    postRepository.save(post);
                }
        );
    }

    private Board board(String boardName) {
        return boardRepository.findByName(boardName).orElseThrow(BoardNotFoundException::new);
    }
}
