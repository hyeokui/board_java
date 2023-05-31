package com.example.demo.domain.domain.comment.service.recommend.update;

import com.example.demo.domain.domain.comment.domain.Comment;
import com.example.demo.domain.domain.comment.domain.CommentRecommendRepository;
import com.example.demo.domain.domain.comment.domain.CommentRepository;
import com.example.demo.enums.common.RecommendStatus;
import com.example.demo.exception.comment.CommentAlreadyRecommendedException;
import com.example.demo.exception.comment.CommentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentRecommendUpdateServiceImpl {

    private final CommentRepository commentRepository;
    private final CommentRecommendRepository commentRecommendRepository;

    public void update(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        commentRecommendRepository.findByUserIdAndCommentIdAndRecommendStatus(userId, commentId, RecommendStatus.UNRECOMMENDED).ifPresentOrElse(commentRecommend -> {
                    commentRecommend.update();
                    comment.increaseRecommendCount();
                },
                () -> {
                    throw new CommentAlreadyRecommendedException();
                }
        );
    }
}
