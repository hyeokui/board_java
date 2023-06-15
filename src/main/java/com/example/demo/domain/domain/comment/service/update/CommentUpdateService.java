package com.example.demo.domain.domain.comment.service.update;

import com.example.demo.exception.comment.CommentNotFoundException;

public interface CommentUpdateService {

    /**
     * 해당 사용자가 작성한 댓글인지 확인후 내용을 수정함
     *
     * @param userId    :사용자의 고유 아이디값
     * @param commentId :댓글 고유 아이디값
     * @param content   :댓글 내용
     * @throws CommentNotFoundException:해당하는 댓글을 찾을 수 없을시
     **/
    void update(Long commentId, Long userId, String content);
}
