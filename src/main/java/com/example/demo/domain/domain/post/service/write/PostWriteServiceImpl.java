package com.example.demo.domain.domain.post.service.write;

import com.example.demo.domain.domain.admin.domain.AdminRepository;
import com.example.demo.domain.domain.admin.service.permission.PermissionCheckService;
import com.example.demo.domain.domain.board.domain.Board;
import com.example.demo.domain.domain.board.domain.BoardRepository;
import com.example.demo.domain.domain.post.domain.Post;
import com.example.demo.domain.domain.post.domain.PostRepository;
import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.enums.board.BoardStatus;
import com.example.demo.exception.board.BoardNotOperatingException;
import com.example.demo.exception.user.BoardNotFoundException;
import com.example.demo.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostWriteServiceImpl implements PostWriteService {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final PermissionCheckService permissionCheckService;

    @Override
    public void writeByAdmin(Long adminId, String title, String content, String boardName) {
        permissionCheckService.checkPostPermission(adminId);
        adminRepository.findById(adminId).ifPresent(admin -> {
                    if (isBoardOperating(boardName)) {
                        Post post = new Post(title, content, admin, board(boardName));
                        postRepository.save(post);
                    }
                }
        );
    }

    @Override
    public void writeByUser(String userId, String title, String content, String boardName) {
        userRepository.findOptionalUserByUserId(userId).ifPresentOrElse(user -> {
                    if (isBoardOperating(boardName)) {
                        Post post = new Post(title, content, user, board(boardName));
                        postRepository.save(post);
                    }
                },
                () -> {
                    throw new UserNotFoundException();
                }
        );
    }

    private Board board(String boardName) {
        return boardRepository.findByName(boardName).orElseThrow(BoardNotFoundException::new);
    }

    private boolean isBoardOperating(String boardName) {
        boardRepository.findByName(boardName).ifPresentOrElse(board -> {
                    if (board.getBoardStatus() != BoardStatus.OPERATING) {
                        throw new BoardNotOperatingException();
                    }
                },
                () -> {
                    throw new BoardNotFoundException();
                }
        );
        return true;
    }
}
