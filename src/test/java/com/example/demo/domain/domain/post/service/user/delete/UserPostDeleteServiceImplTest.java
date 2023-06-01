package com.example.demo.domain.domain.post.service.user.delete;

import com.example.demo.domain.domain.board.domain.Board;
import com.example.demo.domain.domain.board.domain.BoardRepository;
import com.example.demo.domain.domain.post.domain.Post;
import com.example.demo.domain.domain.post.domain.PostRepository;
import com.example.demo.domain.domain.user.domain.User;
import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.enums.board.BoardStatus;
import com.example.demo.exception.board.BoardNotFoundException;
import com.example.demo.exception.board.BoardNotOperatingException;
import com.example.demo.exception.post.PostNotFoundException;
import com.example.demo.exception.user.InvalidPasswordException;
import com.example.demo.exception.user.UserNotFoundException;
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
class UserPostDeleteServiceImplTest {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;

    private final UserPostDeleteService userPostDeleteService;

    @Autowired
    public UserPostDeleteServiceImplTest(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, PostRepository postRepository, BoardRepository boardRepository, UserPostDeleteService userPostDeleteService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.boardRepository = boardRepository;
        this.userPostDeleteService = userPostDeleteService;
    }

    @Test
    void 글삭제_정상작동() {
        //given
        User user = new User("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", "test_phone");
        userRepository.save(user);
        Long userId = user.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);
        String boardName = board.getName();

        Post post = new Post("test_title", "test_content", user, board);
        postRepository.save(post);
        Long postId = post.getId();

        //when
        userPostDeleteService.delete(postId, userId, "test_password", boardName);

        //then
        assertEquals("DELETED", post.getPostStatus().name());
    }

    @Test
    void 글삭제_없는사용자_비정상작동() {
        //given
        User user = new User("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", "test_phone");
        userRepository.save(user);
        Long userId = user.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);
        String boardName = board.getName();

        Post post = new Post("test_title", "test_content", user, board);
        postRepository.save(post);
        Long postId = post.getId();

        //when
        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class,
                () -> userPostDeleteService.delete(postId, userId + 1, "test_password", boardName)
        );

        //then
        assertEquals("This ID could not be found", userNotFoundException.getMessage());
    }

    @Test
    void 글삭제_비밀번호오류_비정상작동() {
        //given
        User user = new User("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", "test_phone");
        userRepository.save(user);
        Long userId = user.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);
        String boardName = board.getName();

        Post post = new Post("test_title", "test_content", user, board);
        postRepository.save(post);
        Long postId = post.getId();

        //when
        InvalidPasswordException invalidPasswordException = assertThrows(InvalidPasswordException.class,
                () -> userPostDeleteService.delete(postId, userId, "wrong_password", boardName)
        );

        //then
        assertEquals("Invalid password", invalidPasswordException.getMessage());
    }

    @Test
    void 글삭제_없는게시물_비정상작동() {
        //given
        User user = new User("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", "test_phone");
        userRepository.save(user);
        Long userId = user.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);
        String boardName = board.getName();

        Post post = new Post("test_title", "test_content", user, board);
        postRepository.save(post);
        Long postId = post.getId();

        //when
        PostNotFoundException postNotFoundException = assertThrows(PostNotFoundException.class,
                () -> userPostDeleteService.delete(postId + 1, userId, "test_password", boardName)
        );

        //then
        assertEquals("This post could not be found", postNotFoundException.getMessage());
    }

    @Test
    void 글삭제_없는게시판_비정상작동() {
        //given
        User user = new User("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", "test_phone");
        userRepository.save(user);
        Long userId = user.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);
        String boardName = board.getName();

        Post post = new Post("test_title", "test_content", user, board);
        postRepository.save(post);
        Long postId = post.getId();

        //when
        BoardNotFoundException boardNotFoundException = assertThrows(BoardNotFoundException.class,
                () -> userPostDeleteService.delete(postId, userId, "test_password", boardName + 1)
        );

        //then
        assertEquals("This board could not be found", boardNotFoundException.getMessage());
    }

    @Test
    void 글삭제_비운영게시판_비정상작동() {
        //given
        User user = new User("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", "test_phone");
        userRepository.save(user);
        Long userId = user.getId();

        Board board = new Board("test_board", BoardStatus.NOT_OPERATING);
        boardRepository.save(board);
        String boardName = board.getName();

        Post post = new Post("test_title", "test_content", user, board);
        postRepository.save(post);
        Long postId = post.getId();

        //when
        BoardNotOperatingException boardNotOperatingException = assertThrows(BoardNotOperatingException.class,
                () -> userPostDeleteService.delete(postId, userId, "test_password", boardName)
        );

        //then
        assertEquals("This board is not currently operating", boardNotOperatingException.getMessage());
    }
}