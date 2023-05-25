package com.example.demo.domain.domain.post.service.user.update;

import com.example.demo.exception.board.BoardNotFoundException;
import com.example.demo.exception.board.BoardNotOperatingException;
import com.example.demo.exception.post.PostNotFoundException;
import com.example.demo.exception.user.UserNotFoundException;

public interface UserPostUpdateService {

    /**
     * 해당하는 사용자와 게시판의 고유 값을 가지고 있는 게시물을 확인합니다
     * 확인될시 게시물을 작성하려는 해당 게시판의 운영상태를 확인합니다
     * 해당 게시판이 운영중일시 게시물의 제목과 내용등을 수정할 수 있습니다
     * 앞의 사용자와 게시판의 고유값에 해당하는 게시물이 없을시 또는 게시판이 운영중이 아닐시 각 익셉션이 발생합니다
     *
     * @param userId  :사용자의 고유 아이디 값
     * @param boardId :게시판의 고유 아이디 값
     * @param title   : 작성하는 글의 제목
     * @param content : 작성하는 글의 내용
     * @throws BoardNotFoundException     : 해당하는 게시판을 찾을 수 없을시
     * @throws BoardNotOperatingException : 해당 게시판이 운영하지 않는 상태의 경우
     * @throws PostNotFoundException:     : 해당하는 게시물을 찾을 수 없을시
     **/
    void update(Long userId, Long boardId, String title, String content);
}
