package com.example.demo.domain.domain.user.service.registration;

import com.example.demo.domain.domain.user.service.registration.dto.UserRegistrationServiceDto;
import com.example.demo.exception.user.DuplicateUserIdException;

public interface UserRegistrationService {

    /**
     * 유저가 회원가입에 필요한 정보들을 입력하면 회원가입 dto로 값을 전달받아 아이디에 대한 중복체크를 한 뒤 저장 ->(비밀번호 암호화)
     *
     * @param userRegistrationServiceDto: 유저가 회원가입에 필요한 입력요소들을 가지고 있음(ex. 아이디,비밀번호,이름..등등)
     * @throws DuplicateUserIdException: 회원가입에 필요한 유저아이디가 중복되는 값일때 발생하는 익셉션
     **/
    void add(UserRegistrationServiceDto userRegistrationServiceDto);
}
