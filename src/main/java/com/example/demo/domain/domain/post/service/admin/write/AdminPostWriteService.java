package com.example.demo.domain.domain.post.service.admin.write;

import com.example.demo.exception.admin.AdminNotFoundException;
import com.example.demo.exception.admin.InsufficientPermissionException;
import com.example.demo.exception.board.BoardNotFoundException;
import com.example.demo.exception.board.BoardNotOperatingException;

public interface AdminPostWriteService {

    /**
     * 해당 관리자의 유무와 해당 게시판의 권한이 있는지 확인합니다
     * 권한이 있을시 해당 게시판의 운영상태를 확인합니다
     * 게시판 운영시 제목과 내용을 가지고 관리자 게시물을 등록합니다
     * 존재하지 않는 관리자이거나 권한이 없거나 또는 게시판이 운영중이지 않은 상태일시 각각에 해당하는 익셉션이 발생합니다
     *
     * @param adminId   :관리자의 고유한 아이디 값
     * @param title     : 작성하려는 게시물의 제목
     * @param content   :작성하려는 게시물의 내용
     * @param boardName :해당 게시판 이름
     * @throws AdminNotFoundException:          해당하는 관리자를 찾을 수 없을시
     * @throws InsufficientPermissionException: 해당하는 권한이 없을시
     * @throws BoardNotOperatingException:      해당 게시판이 운영하지 않는 상태의 경우
     * @throws BoardNotFoundException:          해당하는 게시판을 찾을 수 없을시
     **/
    void write(Long adminId, String title, String content, String boardName);
}
