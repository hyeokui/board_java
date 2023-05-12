package com.example.demo.domain.domain.user.service.delete;

import com.example.demo.exception.user.InvalidPasswordException;
import com.example.demo.exception.user.UserNotFoundException;

public interface UserDeleteService {

    /**
     * 사용자 고유 아이디값을 사용하여 유저를 찾음
     * 해당 값을 가진 유저가 존재할시 입력값으로 받은 비밀번호가 존재하는 유저의 비밀번호와 일치하는지 확인
     * 일치할시 -> 사용자의 아이디 상태를 비활성화 시킴
     * 일치하지 않을시 -> 오류 발생
     *
     * @param userId   : 유저의 고유 아이디값
     * @param password : 유저가 삭제를 하기 위해 입력하는 비밀번호
     * @throws InvalidPasswordException : 입력한 비밀번호가 유효하지 않을때 발생
     * @throws UserNotFoundException    : 해당하는 유저를 찾을 수 없을때 발생
     **/
    void delete(Long userId, String password);
}
