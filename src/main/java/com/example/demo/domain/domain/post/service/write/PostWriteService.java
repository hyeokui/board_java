package com.example.demo.domain.domain.post.service.write;

public interface PostWriteService {

    void writeByAdmin(Long adminId, String title, String content, String boardName);
    void writeByUser(String userId, String title, String content, String boardName);
}
