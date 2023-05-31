package com.example.demo.domain.domain.comment.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndUserId(Long commentId, Long userId);
}
