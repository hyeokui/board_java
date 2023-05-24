package com.example.demo.domain.domain.board.service.delete;

import com.example.demo.domain.domain.admin.domain.Admin;
import com.example.demo.domain.domain.admin.domain.AdminRepository;
import com.example.demo.domain.domain.board.domain.Board;
import com.example.demo.domain.domain.board.domain.BoardRepository;
import com.example.demo.enums.admin.AdminStatus;
import com.example.demo.enums.board.BoardStatus;
import com.example.demo.exception.admin.AdminNotFoundException;
import com.example.demo.exception.admin.InsufficientPermissionException;
import com.example.demo.exception.board.BoardNotFoundException;
import com.example.demo.exception.user.InvalidPasswordException;
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
class BoardDeleteServiceImplTest {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final BoardRepository boardRepository;
    private final BoardDeleteService boardDeleteService;

    @Autowired
    public BoardDeleteServiceImplTest(AdminRepository adminRepository, BCryptPasswordEncoder bCryptPasswordEncoder, BoardRepository boardRepository, BoardDeleteService boardDeleteService) {
        this.adminRepository = adminRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.boardRepository = boardRepository;
        this.boardDeleteService = boardDeleteService;
    }

    @Test
    void 게시판삭제_정상작동() {
        //given
        Admin admin = new Admin("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", AdminStatus.BOARD_ADMIN);
        adminRepository.save(admin);
        Long adminId = admin.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);
        Long boardId = board.getId();

        //when
        boardDeleteService.delete(boardId, adminId, "test_password");

        //then
        assertEquals(BoardStatus.NOT_OPERATING, board.getBoardStatus());
    }

    @Test
    void 게시판삭제_없는게시판_비정상작동() {
        //given
        Admin admin = new Admin("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", AdminStatus.BOARD_ADMIN);
        adminRepository.save(admin);
        Long adminId = admin.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);
        Long boardId = board.getId();

        //when
        BoardNotFoundException boardNotFoundException = assertThrows(BoardNotFoundException.class,
                () -> boardDeleteService.delete(boardId + 1, adminId, "test_password")
        );

        //then
        assertEquals("This board could not be found", boardNotFoundException.getMessage());
    }

    @Test
    void 게시판삭제_비밀번호다름_비정상작동() {
        //given
        Admin admin = new Admin("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", AdminStatus.BOARD_ADMIN);
        adminRepository.save(admin);
        Long adminId = admin.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);
        Long boardId = board.getId();

        //when
        InvalidPasswordException invalidPasswordException = assertThrows(InvalidPasswordException.class,
                () -> boardDeleteService.delete(boardId, adminId, "invalid_password")
        );

        //then
        assertEquals("Invalid password", invalidPasswordException.getMessage());
    }

    @Test
    void 게시판삭제_없는관리자_비정상작동() {
        //given
        Admin admin = new Admin("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", AdminStatus.BOARD_ADMIN);
        adminRepository.save(admin);
        Long adminId = admin.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);
        Long boardId = board.getId();

        //when
        AdminNotFoundException adminNotFoundException = assertThrows(AdminNotFoundException.class,
                () -> boardDeleteService.delete(boardId, adminId + 1, "test_password")
        );

        //then
        assertEquals("This admin could not be found", adminNotFoundException.getMessage());
    }

    @Test
    void 게시판삭제_권한없음_비정상작동() {
        //given
        Admin admin = new Admin("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", AdminStatus.POST_ADMIN);
        adminRepository.save(admin);
        Long adminId = admin.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);
        Long boardId = board.getId();

        //when
        InsufficientPermissionException insufficientPermissionException = assertThrows(InsufficientPermissionException.class,
                () -> boardDeleteService.delete(boardId, adminId, "test_password")
        );

        //then
        assertEquals("Insufficient permission to perform the requested operation", insufficientPermissionException.getMessage());
    }
}