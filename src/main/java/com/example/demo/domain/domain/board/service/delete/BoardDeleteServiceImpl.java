package com.example.demo.domain.domain.board.service.delete;

import com.example.demo.domain.domain.admin.domain.Admin;
import com.example.demo.domain.domain.admin.domain.AdminRepository;
import com.example.demo.domain.domain.admin.service.permission.PermissionCheckService;
import com.example.demo.domain.domain.board.domain.BoardRepository;
import com.example.demo.exception.admin.AdminNotFoundException;
import com.example.demo.exception.board.BoardNotFoundException;
import com.example.demo.exception.user.InvalidPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardDeleteServiceImpl implements BoardDeleteService {

    private final AdminRepository adminRepository;
    private final BoardRepository boardRepository;
    private final PermissionCheckService permissionCheckService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void delete(Long boardId, Long adminId, String password) {
        permissionCheckService.checkBoardPermission(adminId);
        checkAdminAndPassword(adminId, password);

        boardRepository.findById(boardId).ifPresentOrElse(board ->
                        board.delete(password),
                () -> {
                    throw new BoardNotFoundException();
                }
        );
    }

    private void checkAdminAndPassword(Long adminId, String password) {
        adminRepository.findById(adminId).ifPresent(admin -> {
                    if (!bCryptPasswordEncoder.matches(password, admin.getPassword())) {
                        throw new InvalidPasswordException();
                    }
                }
        );
    }
}
