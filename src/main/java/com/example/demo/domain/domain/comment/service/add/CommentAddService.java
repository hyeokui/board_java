package com.example.demo.domain.domain.comment.service.add;

import com.example.demo.exception.post.PostNotFoundException;
import com.example.demo.exception.user.UserNotFoundException;

public interface CommentAddService {

    /**
     * 사용자와 댓글을 달려는 게시물을 확인후 해당 사용자와 게시물의 정보를 가지고 댓글이 생성된다
     *
     * @param userId  :사용자의 고유 아이디값
     * @param postId  : 게시물의 고유 아이디값
     * @param content :댓글 내용
     * @throws UserNotFoundException:해당하는 유저를 찾을 수 없는 경우
     * @throws PostNotFoundException:해당하는 게시물을 찾을 수 없는 경우
     **/
    void add(String content, Long userId, Long postId);
}
