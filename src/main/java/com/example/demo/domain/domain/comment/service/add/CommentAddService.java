package com.example.demo.domain.domain.comment.service.add;

public interface CommentAddService {

    void add(String content, Long userId, Long postId);
}
