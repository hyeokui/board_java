package com.example.demo.domain.domain.post.domain;

import com.example.demo.enums.common.RecommendStatus;
import com.example.demo.enums.post.PostStatus;
import com.example.demo.exception.post.PostNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByAdminId(Long adminId);

    Optional<Post> findByUserIdAndBoardId(Long userId, Long boardId);

    Optional<Post> findByIdAndUserIdAndPostStatus(Long postId, Long userId, PostStatus postStatus);

    Optional<Post> findByIdAndAdminIdAndPostStatus(Long postId, Long adminId, PostStatus postStatus);

    default Post validatePost(Long postId) {
        return findById(postId).orElseThrow(PostNotFoundException::new);
    }
}
