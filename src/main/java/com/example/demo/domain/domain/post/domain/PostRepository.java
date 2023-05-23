package com.example.demo.domain.domain.post.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByAdminId(Long adminId);

    Optional<Post> findByUserId(Long userId);
}
