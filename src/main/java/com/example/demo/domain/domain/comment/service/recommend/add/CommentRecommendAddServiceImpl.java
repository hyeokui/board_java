package com.example.demo.domain.domain.comment.service.recommend.add;

import com.example.demo.domain.domain.comment.domain.Comment;
import com.example.demo.domain.domain.comment.domain.CommentRecommend;
import com.example.demo.domain.domain.comment.domain.CommentRecommendRepository;
import com.example.demo.domain.domain.comment.domain.CommentRepository;
import com.example.demo.domain.domain.user.domain.User;
import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.enums.common.RecommendStatus;
import com.example.demo.exception.comment.CommentAlreadyRecommendedException;
import com.example.demo.exception.comment.CommentNotFoundException;
import com.example.demo.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentRecommendAddServiceImpl implements CommentRecommendAddService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentRecommendRepository commentRecommendRepository;

    @Override
    public void add(Long userId, Long commentId) {
        checkDuplicateUserRecommendation(userId, commentId);

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        CommentRecommend commentRecommend = new CommentRecommend(user, comment, RecommendStatus.RECOMMEND);
        commentRecommendRepository.save(commentRecommend);

        comment.increaseRecommendCount();
        commentRepository.save(comment);
    }

    private void checkDuplicateUserRecommendation(Long userId, Long commentId) {
        if (commentRecommendRepository.existsByUserIdAndCommentIdAndRecommendStatus(userId, commentId, RecommendStatus.RECOMMEND)) {
            throw new CommentAlreadyRecommendedException();
        }
    }
}
