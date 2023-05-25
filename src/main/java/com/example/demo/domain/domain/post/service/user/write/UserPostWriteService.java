package com.example.demo.domain.domain.post.service.user.write;

import com.example.demo.exception.board.BoardNotFoundException;
import com.example.demo.exception.board.BoardNotOperatingException;
import com.example.demo.exception.user.UserNotFoundException;

public interface UserPostWriteService {

    /**
     * 해당하는 사용자가 확인될시 게시물을 작성하려는 해당 게시판의 운영상태를 확인합니다
     * 해당 게시판이 운영중일시 제목과 내용을 작성하여 게시물을 등록합니다
     * 앞서 확인했던 사용자가 없을시 또는 게시판이 운영중이 아닐시 각 익셉션이 발생합니다
     *
     * @param userId    :사용자의 고유 아이디 값
     * @param title     : 작성하려는 게시물의 제목
     * @param content   : 작성하려는 게시물의 내용
     * @param boardName :게시판의 이름
     * @throws UserNotFoundException      : 해당하는 사용자를 찾을 수 없을시
     * @throws BoardNotFoundException     : 해당하는 게시판을 찾을 수 없을시
     * @throws BoardNotOperatingException : 해당 게시판이 운영하지 않는 상태의 경우
     **/
    void write(Long userId, String title, String content, String boardName);
}
