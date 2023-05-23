package com.example.demo.domain.domain.post.service.update;

public interface PostUpdateService {

    void updateByAdmin(Long adminId, String title, String content);
    void updateByUser(Long userId, String title, String content);
}
