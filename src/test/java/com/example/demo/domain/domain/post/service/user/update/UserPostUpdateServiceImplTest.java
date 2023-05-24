package com.example.demo.domain.domain.post.service.user.update;

import com.example.demo.domain.domain.board.domain.Board;
import com.example.demo.domain.domain.board.domain.BoardRepository;
import com.example.demo.domain.domain.post.domain.Post;
import com.example.demo.domain.domain.post.domain.PostRepository;
import com.example.demo.domain.domain.user.domain.User;
import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.enums.board.BoardStatus;
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
class UserPostUpdateServiceImplTest {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final UserPostUpdateService userPostUpdateService;

    @Autowired
    public UserPostUpdateServiceImplTest(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, PostRepository postRepository, BoardRepository boardRepository, UserPostUpdateService userPostUpdateService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.boardRepository = boardRepository;
        this.userPostUpdateService = userPostUpdateService;
    }

    @Test
    void 글수정_정상작동() {
        //given
        User user = new User("test_id", "test_password", "test_name", "test_email", "test_phone");
        userRepository.save(user);
        Long userId = user.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);

        Post post = new Post("test_title", "test_content", user, board);
        postRepository.save(post);

        //when
        userPostUpdateService.update(userId, "update_title", "update_content");

        //then
        assertEquals("update_title", post.getTitle());
        assertEquals("update_content", post.getContent());
    }

    @Test
    void 글수정_없는사용자_비정상작동() {
        //given
        User user = new User("test_id", "test_password", "test_name", "test_email", "test_phone");
        userRepository.save(user);
        Long userId = user.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);

        Post post = new Post("test_title", "test_content", user, board);
        postRepository.save(post);

        //when
        PostNotFoundException postNotFoundException = assertThrows(PostNotFoundException.class,
                () -> userPostUpdateService.update(userId + 1, "update_title", "update_content")
        );

        //then
        assertEquals("This post could not be found", postNotFoundException.getMessage());
    }
}