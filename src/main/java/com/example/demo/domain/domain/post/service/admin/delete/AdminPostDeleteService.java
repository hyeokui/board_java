package com.example.demo.domain.domain.post.service.admin.delete;

import com.example.demo.exception.admin.AdminNotFoundException;
import com.example.demo.exception.admin.InsufficientPermissionException;
import com.example.demo.exception.board.BoardNotFoundException;
import com.example.demo.exception.board.BoardNotOperatingException;
import com.example.demo.exception.post.PostNotFoundException;
import com.example.demo.exception.user.InvalidPasswordException;

public interface AdminPostDeleteService {

    /**
     * 해당 게시물 아이디 값을 가진 활성화된 관리자의 게시물을 찾습니다
     * 후에 관리자의 해당 권한 체크와 게시판 운영상태를 확인합니다
     * 게시물이 존재 할 경우 사용자의 비밀번호를 확인하는 과정을 거친 후 올바를시 게시물은 삭제상태로 변환됩니다
     * 관리자 권한이 없는 경우, 게시판이 운영중이지 않는 경우,
     * 비밀번호를 검증하지 못하거나 게시물이 존재하지 않는 경우는 각각에 해당하는 익셉션이 발생합니다
     *
     * @param postId    : 게사물의 고유 아이디 값
     * @param adminId   : 관리자의 고유 아이디 값
     * @param password  : 관리자의 비밀번호
     * @param boardName : 해당 게시판 이름
     * @throws AdminNotFoundException          :  해당하는 관리자를 찾을 수 없을시
     * @throws InsufficientPermissionException :  해당하는 권한이 없을시
     * @throws InvalidPasswordException        :  비밀번호가 유효하지 않을시
     * @throws BoardNotOperatingException      :  해당 게시판이 운영하지 않는 상태의 경우
     * @throws BoardNotFoundException          :  해당하는 게시판을 찾을 수 없을시
     * @throws PostNotFoundException           :  해당하는 게시물을 찾을 수 없을시
     **/
    void delete(Long postId, Long adminId, String password, String boardName);
}
