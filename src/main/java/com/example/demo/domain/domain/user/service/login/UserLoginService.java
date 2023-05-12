package com.example.demo.domain.domain.user.service.login;

import com.example.demo.exception.user.InvalidPasswordException;
import com.example.demo.exception.user.UserNotFoundException;

public interface UserLoginService {

    /**
     * 유저의 아이디가 존재하는지 조회후 없을시 -> 익셉션 발생, 있을시 -> 유저의 아이디 값을 가지고 있는 유저 객체를 넘겨줌
     * 후에 넘겨 받은 유저 객체의 암호화된 비밀번호를 입력받은 비밀번호와 비교해준뒤 다를시 -> 익셉션 발생
     * 맞을시 -> 유저의 고유 아이디 값을 반환해준뒤 로그인에 성공한다.
     *
     * @param userId   : 사용자 입력 아이디
     * @param password : 사용자 입력 비밀번호
     * @return 로그인한 유저의 고유 아이디 값
     * @throws UserNotFoundException    : 해당 유저를 찾을수 없을시 발생
     * @throws InvalidPasswordException : 비밀번호가 유효하지 않을시 발생
     **/
    Long login(String userId, String password);
}
