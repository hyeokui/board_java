package com.example.demo.domain.domain.post.service.admin.write;

public interface AdminPostWriteService {

    void write(Long adminId, String title, String content, String boardName);
}
