package com.example.demo.domain.domain.board.service.create;

import com.example.demo.exception.InsufficientPermissionException;
import com.example.demo.exception.user.AdminNotFoundException;

public interface BoardCreateService {

    /**
     * 관리자의 권한 확인후 게시판 이름을 가지고 생성
     * 존재하지 않는 관리자이거나 권한이 없을시 익셉션 발생
     *
     * @param adminId   : 관리자 아이디(닉네임)
     * @param boardName :생성할 게시판 이름
     * @throws AdminNotFoundException          : 해당하는 관리자를 찾을 수 없을시
     * @throws InsufficientPermissionException : 해당하는 권한이 없을시
     **/
    void create(String adminId, String boardName);
}
