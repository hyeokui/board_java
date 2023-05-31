package com.example.demo.domain.domain.post.service.admin.delete;

import com.example.demo.domain.domain.admin.domain.Admin;
import com.example.demo.domain.domain.admin.domain.AdminRepository;
import com.example.demo.domain.domain.board.domain.Board;
import com.example.demo.domain.domain.board.domain.BoardRepository;
import com.example.demo.domain.domain.post.domain.Post;
import com.example.demo.domain.domain.post.domain.PostRepository;
import com.example.demo.enums.admin.AdminStatus;
import com.example.demo.enums.board.BoardStatus;
import com.example.demo.exception.admin.AdminNotFoundException;
import com.example.demo.exception.admin.InsufficientPermissionException;
import com.example.demo.exception.board.BoardNotFoundException;
import com.example.demo.exception.board.BoardNotOperatingException;
import com.example.demo.exception.post.PostNotFoundException;
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
class AdminPostDeleteServiceImplTest {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AdminRepository adminRepository;
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final AdminPostDeleteService adminPostDeleteService;

    @Autowired
    public AdminPostDeleteServiceImplTest(BCryptPasswordEncoder bCryptPasswordEncoder, AdminRepository adminRepository, PostRepository postRepository, BoardRepository boardRepository, AdminPostDeleteService adminPostDeleteService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.adminRepository = adminRepository;
        this.postRepository = postRepository;
        this.boardRepository = boardRepository;
        this.adminPostDeleteService = adminPostDeleteService;
    }

    @Test
    void 글삭제_정상작동() {
        //given
        Admin admin = new Admin("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", AdminStatus.POST_ADMIN);
        adminRepository.save(admin);
        Long adminId = admin.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);
        String boardName = board.getName();

        Post post = new Post("test_title", "test_content", admin, board);
        postRepository.save(post);
        Long postId = post.getId();

        //when
        adminPostDeleteService.delete(postId, adminId, "test_password", boardName);

        //then
        assertEquals("DELETED", post.getPostStatus().name());
    }

    @Test
    void 글삭제_없는게시물_비정상작동() {
        //given
        Admin admin = new Admin("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", AdminStatus.POST_ADMIN);
        adminRepository.save(admin);
        Long adminId = admin.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);
        String boardName = board.getName();

        Post post = new Post("test_title", "test_content", admin, board);
        postRepository.save(post);
        Long postId = post.getId();

        //when
        PostNotFoundException postNotFoundException = assertThrows(PostNotFoundException.class,
                () -> adminPostDeleteService.delete(postId + 1, adminId, "test_password", boardName)
        );

        //then
        assertEquals("This post could not be found", postNotFoundException.getMessage());
    }

    @Test
    void 글삭제_없는관리자_비정상작동() {
        //given
        Admin admin = new Admin("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", AdminStatus.POST_ADMIN);
        adminRepository.save(admin);
        Long adminId = admin.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);
        String boardName = board.getName();

        Post post = new Post("test_title", "test_content", admin, board);
        postRepository.save(post);
        Long postId = post.getId();

        //when
        AdminNotFoundException adminNotFoundException = assertThrows(AdminNotFoundException.class,
                () -> adminPostDeleteService.delete(postId, adminId + 1, "test_password", boardName)
        );

        //then
        assertEquals("This admin could not be found", adminNotFoundException.getMessage());
    }

    @Test
    void 글삭제_권한없음_비정상작동() {
        //given
        Admin admin = new Admin("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", AdminStatus.BOARD_ADMIN);
        adminRepository.save(admin);
        Long adminId = admin.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);
        String boardName = board.getName();

        Post post = new Post("test_title", "test_content", admin, board);
        postRepository.save(post);
        Long postId = post.getId();

        //when
        InsufficientPermissionException insufficientPermissionException = assertThrows(InsufficientPermissionException.class,
                () -> adminPostDeleteService.delete(postId, adminId, "test_password", boardName)
        );

        //then
        assertEquals("Insufficient permission to perform the requested operation", insufficientPermissionException.getMessage());
    }

    @Test
    void 글삭제_비밀번호다름_비정상작동() {
        //given
        Admin admin = new Admin("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", AdminStatus.POST_ADMIN);
        adminRepository.save(admin);
        Long adminId = admin.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);
        String boardName = board.getName();

        Post post = new Post("test_title", "test_content", admin, board);
        postRepository.save(post);
        Long postId = post.getId();

        //when
        InvalidPasswordException invalidPasswordException = assertThrows(InvalidPasswordException.class,
                () -> adminPostDeleteService.delete(postId, adminId, "wrong_password", boardName)
        );

        //then
        assertEquals("Invalid password", invalidPasswordException.getMessage());
    }

    @Test
    void 글삭제_없는게시판_비정상작동() {
        //given
        Admin admin = new Admin("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", AdminStatus.POST_ADMIN);
        adminRepository.save(admin);
        Long adminId = admin.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);
        String boardName = board.getName();

        Post post = new Post("test_title", "test_content", admin, board);
        postRepository.save(post);
        Long postId = post.getId();

        //when
        BoardNotFoundException boardNotFoundException = assertThrows(BoardNotFoundException.class,
                () -> adminPostDeleteService.delete(postId, adminId, "test_password", boardName + 1)
        );

        //then
        assertEquals("This board could not be found", boardNotFoundException.getMessage());
    }

    @Test
    void 글삭제_비운영게시판_비정상작동() {
        //given
        Admin admin = new Admin("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", AdminStatus.POST_ADMIN);
        adminRepository.save(admin);
        Long adminId = admin.getId();

        Board board = new Board("test_board", BoardStatus.NOT_OPERATING);
        boardRepository.save(board);
        String boardName = board.getName();

        Post post = new Post("test_title", "test_content", admin, board);
        postRepository.save(post);
        Long postId = post.getId();

        //when
        BoardNotOperatingException boardNotOperatingException = assertThrows(BoardNotOperatingException.class,
                () -> adminPostDeleteService.delete(postId, adminId, "test_password", boardName)
        );

        //then
        assertEquals("This board is not currently operating", boardNotOperatingException.getMessage());
    }
}
