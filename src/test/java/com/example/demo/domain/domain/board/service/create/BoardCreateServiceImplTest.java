package com.example.demo.domain.domain.board.service.create;

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
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BoardCreateServiceImplTest {

    private final AdminRepository adminRepository;
    private final BoardRepository boardRepository;
    private final BoardCreateService boardCreateService;

    @Autowired
    public BoardCreateServiceImplTest(AdminRepository adminRepository, BoardRepository boardRepository, BoardCreateService boardCreateService) {
        this.adminRepository = adminRepository;
        this.boardRepository = boardRepository;
        this.boardCreateService = boardCreateService;
    }

    @Test
    void 게시판생성_정상작동() {
        //given
        Admin admin = new Admin("test_id", "test_password", "test_name", "test_email", AdminStatus.BOARD_ADMIN);
        adminRepository.save(admin);
        Long adminId = admin.getId();

        //when
        boardCreateService.create(adminId, "test_board");
        Board board = boardRepository.findByName("test_board").orElseThrow(BoardNotFoundException::new);

        //then
        assertNotNull(board);
        assertEquals("test_board", board.getName());
        assertEquals(BoardStatus.OPERATING, board.getBoardStatus());
    }

    @Test
    void 게시판생성_권한없음_비정상작동() {
        //given
        Admin admin = new Admin("test_id", "test_password", "test_name", "test_email", AdminStatus.POST_ADMIN);
        adminRepository.save(admin);
        Long adminId = admin.getId();

        //when
        InsufficientPermissionException insufficientPermissionException = assertThrows(InsufficientPermissionException.class,
                () -> boardCreateService.create(adminId, "test_board")
        );

        //then
        assertEquals("Insufficient permission to perform the requested operation", insufficientPermissionException.getMessage());
    }

    @Test
    void 게시판생성_없는관리자_비정상작동() {
        //given
        Admin admin = new Admin("test_id", "test_password", "test_name", "test_email", AdminStatus.BOARD_ADMIN);
        adminRepository.save(admin);
        Long adminId = admin.getId();

        //when
        AdminNotFoundException adminNotFoundException = assertThrows(AdminNotFoundException.class,
                () -> boardCreateService.create(adminId + 1, "test_board")
        );

        //then
        assertEquals("This admin could not be found", adminNotFoundException.getMessage());


    }
}