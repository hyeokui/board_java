package com.example.demo.domain.domain.post.service.user.write;

public interface UserPostWriteService {

    void write(Long userId, String title, String content, String boardName);
}
