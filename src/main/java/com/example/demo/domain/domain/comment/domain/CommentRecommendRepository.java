package com.example.demo.domain.domain.comment.domain;

import com.example.demo.enums.common.RecommendStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRecommendRepository extends JpaRepository<CommentRecommend, Long> {

    boolean existsByUserIdAndCommentIdAndRecommendStatus(Long userId, Long commentId, RecommendStatus recommendStatus);

    Optional<CommentRecommend> findByUserIdAndCommentIdAndRecommendStatus(Long userId, Long commentId, RecommendStatus recommendStatus);
}
