package com.example.demo.domain.domain.post.domain;

import com.example.demo.enums.common.RecommendStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRecommendRepository extends JpaRepository<PostRecommend, Long> {

    Optional<PostRecommend> findByUserIdAndPostId(Long userId, Long postId);

    boolean existsByUserIdAndPostIdAndRecommendStatus(Long userId, Long postId, RecommendStatus recommendStatus);

    Optional<PostRecommend> findByUserIdAndPostIdAndRecommendStatus(Long userId, Long postId, RecommendStatus recommendStatus);

}
