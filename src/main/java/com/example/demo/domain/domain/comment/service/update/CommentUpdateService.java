package com.example.demo.domain.domain.comment.service.update;

public interface CommentUpdateService {

    void update(Long commentId, Long userId, String content);
}
