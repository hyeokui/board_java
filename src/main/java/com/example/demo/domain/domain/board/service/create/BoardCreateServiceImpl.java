package com.example.demo.domain.domain.board.service.create;

import com.example.demo.domain.domain.admin.service.permission.PermissionCheckService;
import com.example.demo.domain.domain.board.domain.Board;
import com.example.demo.domain.domain.board.domain.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardCreateServiceImpl implements BoardCreateService {

    private final BoardRepository boardRepository;
    private final PermissionCheckService permissionCheckService;

    @Override
    public void create(String adminId, String boardName) {
        permissionCheckService.permissionCheck(adminId);

        Board board = new Board(boardName);
        boardRepository.save(board);
    }
}
