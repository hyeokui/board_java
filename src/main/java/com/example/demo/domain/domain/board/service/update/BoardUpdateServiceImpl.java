package com.example.demo.domain.domain.board.service.update;

import com.example.demo.domain.domain.admin.service.permission.PermissionCheckService;
import com.example.demo.domain.domain.board.domain.BoardRepository;
import com.example.demo.exception.user.BoardNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardUpdateServiceImpl implements BoardUpdateService {

    private final BoardRepository boardRepository;
    private final PermissionCheckService permissionCheckService;

    @Override
    public void update(Long adminId, Long boardId, String boardName) {
        permissionCheckService.checkBoardPermission(adminId);

        boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new)
                .update(boardName);
    }
}
