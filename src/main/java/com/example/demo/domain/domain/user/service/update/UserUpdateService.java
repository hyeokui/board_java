package com.example.demo.domain.domain.user.service.update;

import com.example.demo.domain.domain.user.service.update.dto.UserUpdateServiceDto;

public interface UserUpdateService {

    /**
     * 유저 고유 아이디값을 통해 유저를 찾고 존재할시 유저정보를 수정 할 값들을 dto로 받아서 수정해준다.
     *
     * @param userUpdateServiceDto: 유저 정보를 수정 할 값들을 가지고 있음
     **/
    void update(Long userId, UserUpdateServiceDto userUpdateServiceDto);
}
