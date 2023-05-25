package com.example.demo.domain.domain.post.service.user.delete;

import com.example.demo.exception.post.PostNotFoundException;
import com.example.demo.exception.user.InvalidPasswordException;
import com.example.demo.exception.user.UserNotFoundException;

public interface UserPostDeleteService {

    /**
     * 해당하는 사용자가 쓴 게시물중 활성화 상태인것을 찾습니다
     * 게시물이 존재 할 경우 사용자의 비밀번호를 확인하는 과정을 거친 후 올바를시 게시물은 삭제상태로 변환됩니다
     * 비밀번호를 검증하지 못하거나 게시물이 존재하지 않는 경우는 각각에 해당하는 익셉션이 발생합니다
     *
     * @param userId    :사용자의 고유 아이디 값
     * @param password  :사용자의 비밀번호
     * @param postId    : 게사물의 고유 아이디 값
     * @param boardName :해당 게시판 이름
     * @throws UserNotFoundException    : 해당하는 사용자를 찾을 수 없을시
     * @throws InvalidPasswordException : 비밀번호가 유효하지 않을시
     * @throws PostNotFoundException    : 해당하는 게시물을 찾을 수 없을시
     **/
    void delete(Long postId, Long userId, String password, String boardName);
}
