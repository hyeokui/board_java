package com.example.demo.domain.domain.post.service.admin.update;

import com.example.demo.exception.admin.AdminNotFoundException;
import com.example.demo.exception.admin.InsufficientPermissionException;
import com.example.demo.exception.board.BoardNotFoundException;
import com.example.demo.exception.board.BoardNotOperatingException;
import com.example.demo.exception.post.PostNotFoundException;

public interface AdminPostUpdateService {

    /**
     * 해당 게시물 아이디 값을 가진 활성화된 관리자의 게시물을 찾습니다
     * 후에 관리자의 해당 권한 체크와 게시판 운영상태를 확인합니다
     * 해당 조건들이 모두 만족할시 게시물을 수정합니다
     * 과정중에 관리자의 권한이 없거나 게시판이 운영상태가 아닐경우 또는 게시물이 존재하지 않을 경우 각각에 따른 익셉션이 발생합니다
     *
     * @param postId    :게시물의 고유 아이디 값
     * @param adminId   :관리자의 고유 아이디 값
     * @param title     :수정 될 제목
     * @param content   :수정 될 내용
     * @param boardName :해당 게시판 이름
     * @throws AdminNotFoundException          :  해당하는 관리자를 찾을 수 없을시
     * @throws InsufficientPermissionException :  해당하는 권한이 없을시
     * @throws BoardNotOperatingException      :  해당 게시판이 운영하지 않는 상태의 경우
     * @throws BoardNotFoundException          :  해당하는 게시판을 찾을 수 없을시
     * @throws PostNotFoundException           :  해당하는 게시물을 찾을 수 없을시
     **/
    void update(Long postId, Long adminId, String title, String content, String boardName);
}
