package com.example.demo.domain.domain.post.service.admin.write;

import com.example.demo.domain.domain.admin.domain.Admin;
import com.example.demo.domain.domain.admin.domain.AdminRepository;
import com.example.demo.domain.domain.board.domain.Board;
import com.example.demo.domain.domain.board.domain.BoardRepository;
import com.example.demo.domain.domain.post.domain.Post;
import com.example.demo.domain.domain.post.domain.PostRepository;
import com.example.demo.domain.domain.post.service.admin.write.AdminPostWriteService;
import com.example.demo.enums.admin.AdminStatus;
import com.example.demo.enums.board.BoardStatus;
import com.example.demo.exception.admin.InsufficientPermissionException;
import com.example.demo.exception.board.BoardNotOperatingException;
import com.example.demo.exception.post.PostNotFoundException;
import com.example.demo.exception.admin.AdminNotFoundException;
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
class AdminPostWriteServiceImplTest {

    private final AdminRepository adminRepository;
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final AdminPostWriteService adminPostWriteService;

    @Autowired
    public AdminPostWriteServiceImplTest(AdminRepository adminRepository, BoardRepository boardRepository, PostRepository postRepository, AdminPostWriteService adminPostWriteService) {
        this.adminRepository = adminRepository;
        this.boardRepository = boardRepository;
        this.postRepository = postRepository;
        this.adminPostWriteService = adminPostWriteService;
    }

    @Test
    void 글작성_관리자_정상작동() {
        //given
        Admin admin = new Admin("test_id", "test_password", "test_name", "test_email", AdminStatus.POST_ADMIN);
        adminRepository.save(admin);
        Long adminId = admin.getId();

        Board board = new Board("test", BoardStatus.OPERATING);
        boardRepository.save(board);
        String boardName = board.getName();

        //when
        adminPostWriteService.write(adminId, "test_title", "test_content", boardName);
        Post post = postRepository.findByAdminId(adminId).orElseThrow(PostNotFoundException::new);

        //then
        assertNotNull(post);
        assertEquals(post.getAdmin().getId(), adminId);
        assertEquals("test_title", post.getTitle());
        assertEquals("test_content", post.getContent());
        assertEquals(post.getBoard().getName(), boardName);
    }

    @Test
    void 글작성_관리자_고유번호다름_비정상작동() {
        //given
        Admin admin = new Admin("test_id", "test_password", "test_name", "test_email", AdminStatus.POST_ADMIN);
        adminRepository.save(admin);
        Long adminId = admin.getId();

        Board board = new Board("test", BoardStatus.OPERATING);
        boardRepository.save(board);
        String boardName = board.getName();

        //when
        AdminNotFoundException adminNotFoundException = assertThrows(AdminNotFoundException.class,
                () -> adminPostWriteService.write(adminId + 1, "test_title", "test_content", boardName)
        );

        //then
        assertEquals("This admin could not be found", adminNotFoundException.getMessage());
    }

    @Test
    void 글작성_관리자_권한없음_비정상작동() {
        //given
        Admin admin = new Admin("test_id", "test_password", "test_name", "test_email", AdminStatus.USER_ADMIN);
        adminRepository.save(admin);
        Long adminId = admin.getId();

        Board board = new Board("test", BoardStatus.OPERATING);
        boardRepository.save(board);
        String boardName = board.getName();

        //when
        InsufficientPermissionException insufficientPermissionException = assertThrows(InsufficientPermissionException.class,
                () -> adminPostWriteService.write(adminId, "test_title", "test_content", boardName)
        );

        //then
        assertEquals("Insufficient permission to perform the requested operation", insufficientPermissionException.getMessage());
    }

    @Test
    void 글작성_없는게시판_비정상작동() {
        //given
        Admin admin = new Admin("test_id", "test_password", "test_name", "test_email", AdminStatus.POST_ADMIN);
        adminRepository.save(admin);
        Long adminId = admin.getId();

        Board board = new Board("test", BoardStatus.OPERATING);
        boardRepository.save(board);
        String boardName = board.getName();

        //when
        BoardNotFoundException boardNotFoundException = assertThrows(BoardNotFoundException.class,
                () -> adminPostWriteService.write(adminId, "test_title", "test_content", boardName + 1)
        );

        //then
        assertEquals("This board could not be found", boardNotFoundException.getMessage());
    }

    @Test
    void 글작성_비운영게시판_비정상작동() {
        //given
        Admin admin = new Admin("test_id", "test_password", "test_name", "test_email", AdminStatus.POST_ADMIN);
        adminRepository.save(admin);
        Long adminId = admin.getId();

        Board board = new Board("test", BoardStatus.NOT_OPERATING);
        boardRepository.save(board);
        String boardName = board.getName();

        //when
        BoardNotOperatingException boardNotOperatingException = assertThrows(BoardNotOperatingException.class,
                () -> adminPostWriteService.write(adminId, "test_title", "test_content", boardName)
        );

        //then
        assertEquals("This board is not currently operating", boardNotOperatingException.getMessage());
    }
}