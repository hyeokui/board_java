package com.example.demo.domain.domain.post.service.user.update;

import com.example.demo.domain.domain.board.domain.BoardRepository;
import com.example.demo.domain.domain.board.service.status.OperatingStatusService;
import com.example.demo.domain.domain.post.domain.Post;
import com.example.demo.domain.domain.post.domain.PostRepository;
import com.example.demo.enums.board.BoardStatus;
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
public class UserPostUpdateServiceImpl implements UserPostUpdateService {

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final OperatingStatusService operatingStatusService;

    @Override
    public void update(Long userId, Long boardId, String title, String content) {
        postRepository.findByUserIdAndBoardId(userId, boardId).ifPresentOrElse(post -> {
                    operatingStatusService.isBoardOperating(getBoardName(boardId));
                    post.update(title, content);
                },
                () -> {
                    throw new PostNotFoundException();
                }
        );
    }

    private String getBoardName(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new).getName();
    }
}
