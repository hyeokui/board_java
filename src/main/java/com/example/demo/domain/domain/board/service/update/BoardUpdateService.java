package com.example.demo.domain.domain.board.service.update;

import com.example.demo.exception.InsufficientPermissionException;
import com.example.demo.exception.user.AdminNotFoundException;
import com.example.demo.exception.user.BoardNotFoundException;

public interface BoardUpdateService {

    /**
     * 관리자 권한 확인후 권한 가지고 있을경우 게시판 이름 변경
     *
     * @param adminId   :관리자 아이디(닉네임)
     * @param boardId   :게시판 고유 아이디
     * @param boardName : 변경될 게시판 이름
     * @throws AdminNotFoundException          : 해당하는 관리자를 찾을 수 없을시
     * @throws InsufficientPermissionException : 해당하는 권한이 없을시
     * @throws BoardNotFoundException          :해당하는 게시판을 찾을 수 없을시
     **/
    void update(String adminId, Long boardId, String boardName);
}
