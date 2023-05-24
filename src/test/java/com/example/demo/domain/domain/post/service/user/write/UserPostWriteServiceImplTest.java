package com.example.demo.domain.domain.post.service.user.write;

import com.example.demo.domain.domain.admin.domain.Admin;
import com.example.demo.domain.domain.board.domain.Board;
import com.example.demo.domain.domain.board.domain.BoardRepository;
import com.example.demo.domain.domain.post.domain.Post;
import com.example.demo.domain.domain.post.domain.PostRepository;
import com.example.demo.domain.domain.post.service.admin.write.AdminPostWriteService;
import com.example.demo.domain.domain.user.domain.User;
import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.enums.admin.AdminStatus;
import com.example.demo.enums.board.BoardStatus;
import com.example.demo.exception.admin.AdminNotFoundException;
import com.example.demo.exception.admin.InsufficientPermissionException;
import com.example.demo.exception.board.BoardNotFoundException;
import com.example.demo.exception.board.BoardNotOperatingException;
import com.example.demo.exception.post.PostNotFoundException;
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
class UserPostWriteServiceImplTest {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final UserPostWriteService userPostWriteService;

    @Autowired
    public UserPostWriteServiceImplTest(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, BoardRepository boardRepository, PostRepository postRepository, UserPostWriteService userPostWriteService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.postRepository = postRepository;
        this.userPostWriteService = userPostWriteService;
    }

    @Test
    void 글작성_사용자_정상작동() {
        //given
        User user = new User("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", "test_phone");
        userRepository.save(user);
        Long userId = user.getId();

        Board board = new Board("test", BoardStatus.OPERATING);
        boardRepository.save(board);
        String boardName = board.getName();

        //when
        userPostWriteService.write(userId, "test_title", "test_content", boardName);
        Post post = postRepository.findByUserId(userId).orElseThrow(PostNotFoundException::new);

        //then
        assertNotNull(post);
        assertEquals(post.getUser().getId(), userId);
        assertEquals("test_title", post.getTitle());
        assertEquals("test_content", post.getContent());
        assertEquals(post.getBoard().getName(), boardName);
    }

    @Test
    void 글작성_사용자_고유번호다름_비정상작동() {
        //given
        User user = new User("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", "test_phone");
        userRepository.save(user);
        Long userId = user.getId();

        Board board = new Board("test", BoardStatus.OPERATING);
        boardRepository.save(board);
        String boardName = board.getName();

        //when
        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class,
                () -> userPostWriteService.write(userId + 1, "test_title", "test_content", boardName)
        );

        //then
        assertEquals("This ID could not be found", userNotFoundException.getMessage());
    }

    @Test
    void 글작성_없는게시판_비정상작동() {
        //given
        User user = new User("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", "test_phone");
        userRepository.save(user);
        Long userId = user.getId();

        Board board = new Board("test", BoardStatus.OPERATING);
        boardRepository.save(board);
        String boardName = board.getName();

        //when
        BoardNotFoundException boardNotFoundException = assertThrows(BoardNotFoundException.class,
                () -> userPostWriteService.write(userId, "test_title", "test_content", boardName + 1)
        );

        //then
        assertEquals("This board could not be found", boardNotFoundException.getMessage());
    }

    @Test
    void 글작성_비운영게시판_비정상작동() {
        //given
        User user = new User("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", "test_phone");
        userRepository.save(user);
        Long userId = user.getId();

        Board board = new Board("test", BoardStatus.NOT_OPERATING);
        boardRepository.save(board);
        String boardName = board.getName();

        //when
        BoardNotOperatingException boardNotOperatingException = assertThrows(BoardNotOperatingException.class,
                () -> userPostWriteService.write(userId, "test_title", "test_content", boardName)
        );

        //then
        assertEquals("This board is not currently operating", boardNotOperatingException.getMessage());
    }
}