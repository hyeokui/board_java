package com.example.demo.domain.domain.post.service.admin.update;

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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AdminPostUpdateServiceImplTest {

    private final AdminRepository adminRepository;
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final AdminPostUpdateService adminPostUpdateService;

    @Autowired
    public AdminPostUpdateServiceImplTest(AdminRepository adminRepository, BoardRepository boardRepository, PostRepository postRepository, AdminPostUpdateService adminPostUpdateService) {
        this.adminRepository = adminRepository;
        this.boardRepository = boardRepository;
        this.postRepository = postRepository;
        this.adminPostUpdateService = adminPostUpdateService;
    }

    @Test
    void 게시물수정_정상작동() {
        //given
        Admin admin = new Admin("test_id", "test_password", "test_name", "test_email", AdminStatus.POST_ADMIN);
        adminRepository.save(admin);
        Long adminId = admin.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);
        String boardName = board.getName();

        Post post = new Post("test_title", "test_content", admin, board);
        postRepository.save(post);
        Long postId = post.getId();

        //when
        adminPostUpdateService.update(postId, adminId, "update_title", "update_content", boardName);

        //then
        assertNotNull(post);
        assertEquals("update_title", post.getTitle());
        assertEquals("update_content", post.getContent());
    }

    @Test
    void 게시물수정_없는게시물_비정상작동() {
        //given
        Admin admin = new Admin("test_id", "test_password", "test_name", "test_email", AdminStatus.POST_ADMIN);
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
                () -> adminPostUpdateService.update(postId + 1, adminId, "update_title", "update_content", boardName)
        );

        //then
        assertEquals("This post could not be found", postNotFoundException.getMessage());
    }

    @Test
    void 게시물수정_없는관리자_비정상작동() {
        //given
        Admin admin = new Admin("test_id", "test_password", "test_name", "test_email", AdminStatus.POST_ADMIN);
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
                () -> adminPostUpdateService.update(postId, adminId + 1, "update_title", "update_content", boardName)
        );

        //then
        assertEquals("This admin could not be found", adminNotFoundException.getMessage());
    }

    @Test
    void 게시물수정_권한없는관리자_비정상작동() {
        //given
        Admin admin = new Admin("test_id", "test_password", "test_name", "test_email", AdminStatus.BOARD_ADMIN);
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
                () -> adminPostUpdateService.update(postId, adminId, "update_title", "update_content", boardName)
        );

        //then
        assertEquals("Insufficient permission to perform the requested operation", insufficientPermissionException.getMessage());

    }

    @Test
    void 게시물수정_비운영게시판_비정상작동() {
        //given
        Admin admin = new Admin("test_id", "test_password", "test_name", "test_email", AdminStatus.POST_ADMIN);
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
                () -> adminPostUpdateService.update(postId, adminId, "update_title", "update_content", boardName)
        );

        //then
        assertEquals("This board is not currently operating", boardNotOperatingException.getMessage());

    }

    @Test
    void 게시물수정_없는게시판_비정상작동() {
        //given
        Admin admin = new Admin("test_id", "test_password", "test_name", "test_email", AdminStatus.POST_ADMIN);
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
                () -> adminPostUpdateService.update(postId, adminId, "update_title", "update_content", boardName + 1)
        );

        //then
        assertEquals("This board could not be found", boardNotFoundException.getMessage());

    }
}