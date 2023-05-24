package com.example.demo.domain.domain.board.service.update;

import com.example.demo.domain.domain.admin.domain.Admin;
import com.example.demo.domain.domain.admin.domain.AdminRepository;
import com.example.demo.domain.domain.board.domain.Board;
import com.example.demo.domain.domain.board.domain.BoardRepository;
import com.example.demo.enums.admin.AdminStatus;
import com.example.demo.enums.board.BoardStatus;
import com.example.demo.exception.admin.AdminNotFoundException;
import com.example.demo.exception.admin.InsufficientPermissionException;
import com.example.demo.exception.board.BoardNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BoardUpdateServiceImplTest {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AdminRepository adminRepository;
    private final BoardRepository boardRepository;
    private final BoardUpdateService boardUpdateService;

    @Autowired
    public BoardUpdateServiceImplTest(BCryptPasswordEncoder bCryptPasswordEncoder, AdminRepository adminRepository, BoardRepository boardRepository, BoardUpdateService boardUpdateService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.adminRepository = adminRepository;
        this.boardRepository = boardRepository;
        this.boardUpdateService = boardUpdateService;
    }

    @Test
    void 게시판수정_정상작동() {
        //given
        Admin admin = new Admin("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", AdminStatus.BOARD_ADMIN);
        adminRepository.save(admin);
        Long adminId = admin.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);
        Long boardId = board.getId();

        //when
        boardUpdateService.update(adminId, boardId, "update_board");

        //then
        assertNotNull(board);
        assertEquals("update_board", board.getName());
    }

    @Test
    void 게시판수정_없는게시판_비정상작동() {
        //given
        Admin admin = new Admin("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", AdminStatus.BOARD_ADMIN);
        adminRepository.save(admin);
        Long adminId = admin.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);
        Long boardId = board.getId();

        //when
        BoardNotFoundException boardNotFoundException = assertThrows(BoardNotFoundException.class,
                () -> boardUpdateService.update(adminId, boardId + 1, "update_board")
        );

        //then
        assertEquals("This board could not be found", boardNotFoundException.getMessage());
    }

    @Test
    void 게시판수정_없는관리자_비정상작동() {
        //given
        Admin admin = new Admin("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", AdminStatus.BOARD_ADMIN);
        adminRepository.save(admin);
        Long adminId = admin.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);
        Long boardId = board.getId();

        //when
        AdminNotFoundException adminNotFoundException = assertThrows(AdminNotFoundException.class,
                () -> boardUpdateService.update(adminId + 1, boardId, "update_board")
        );

        //then
        assertEquals("This admin could not be found", adminNotFoundException.getMessage());
    }

    @Test
    void 게시판수정_권한없음_비정상작동() {
        //given
        Admin admin = new Admin("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", AdminStatus.POST_ADMIN);
        adminRepository.save(admin);
        Long adminId = admin.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);
        Long boardId = board.getId();

        //when
        InsufficientPermissionException insufficientPermissionException = assertThrows(InsufficientPermissionException.class,
                () -> boardUpdateService.update(adminId, boardId, "update_board")
        );

        //then
        assertEquals("Insufficient permission to perform the requested operation", insufficientPermissionException.getMessage());
    }
}