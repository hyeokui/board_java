package com.example.demo.domain.domain.board.service.delete;

import com.example.demo.exception.InsufficientPermissionException;
import com.example.demo.exception.user.AdminNotFoundException;
import com.example.demo.exception.user.BoardNotFoundException;
import com.example.demo.exception.user.InvalidPasswordException;

public interface BoardDeleteService {

    /**
     * 관리자의 권한을 확인후 해당하는 권한을 가진 관리자일 경우 관리자의 아이디와 비밀번호를 확인
     * 후에 해당 관리자의 비밀번호를 이용하여 게시판을 비운영 상태로 변경한다.
     *
     * @param boardId: 게시판 고유 번호
     * @param adminId  :관리자 아이디(닉네임)
     * @param password :관리자 비밀번호
     * @throws AdminNotFoundException          : 해당하는 관리자를 찾을 수 없을시
     * @throws InsufficientPermissionException : 해당하는 권한이 없을시
     * @throws BoardNotFoundException          :해당 게시판을 찾을 수 없을시
     * @throws InvalidPasswordException:       유효하지 않은 비밀번호 입력시
     **/
    void delete(Long boardId, String adminId, String password);
}
