package com.example.demo.domain.domain.comment.service.recommend.cancel;

import com.example.demo.domain.domain.comment.domain.Comment;
import com.example.demo.domain.domain.comment.domain.CommentRecommendRepository;
import com.example.demo.domain.domain.comment.domain.CommentRepository;
import com.example.demo.enums.common.RecommendStatus;
import com.example.demo.exception.comment.CommentNotFoundException;
import com.example.demo.exception.comment.CommentNotRecommendedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentRecommendCancelServiceImpl implements CommentRecommendCancelService {

    private final CommentRepository commentRepository;
    private final CommentRecommendRepository commentRecommendRepository;

    @Override
    public void cancel(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        commentRecommendRepository.findByUserIdAndCommentIdAndRecommendStatus(userId, commentId, RecommendStatus.RECOMMEND).ifPresentOrElse(commentRecommend -> {
                    commentRecommend.cancel();
                    commentRecommendRepository.save(commentRecommend);

                    comment.decreaseRecommendCount();
                    commentRepository.save(comment);
                },
                () -> {
                    throw new CommentNotRecommendedException();
                }
        );
    }
}
