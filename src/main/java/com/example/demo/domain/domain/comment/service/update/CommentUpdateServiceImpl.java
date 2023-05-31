package com.example.demo.domain.domain.comment.service.update;

import com.example.demo.domain.domain.comment.domain.CommentRepository;
import com.example.demo.domain.domain.post.domain.PostRepository;
import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.exception.comment.CommentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentUpdateServiceImpl implements CommentUpdateService {

    private final CommentRepository commentRepository;

    @Override
    public void update(Long commentId, Long userId, String content) {
        commentRepository.findByIdAndUserId(commentId, userId).ifPresentOrElse(comment -> {
                    comment.update(content);
                    commentRepository.save(comment);
                },
                () -> {
                    throw new CommentNotFoundException();
                }
        );
    }
}
