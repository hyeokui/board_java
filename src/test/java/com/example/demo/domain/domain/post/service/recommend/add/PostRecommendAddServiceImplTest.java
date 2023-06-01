package com.example.demo.domain.domain.post.service.recommend.add;

import com.example.demo.domain.domain.board.domain.Board;
import com.example.demo.domain.domain.board.domain.BoardRepository;
import com.example.demo.domain.domain.post.domain.Post;
import com.example.demo.domain.domain.post.domain.PostRecommend;
import com.example.demo.domain.domain.post.domain.PostRecommendRepository;
import com.example.demo.domain.domain.post.domain.PostRepository;
import com.example.demo.domain.domain.post.service.user.update.UserPostUpdateService;
import com.example.demo.domain.domain.user.domain.User;
import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.enums.board.BoardStatus;
import com.example.demo.enums.common.RecommendStatus;
import com.example.demo.exception.post.PostAlreadyRecommendedException;
import com.example.demo.exception.post.PostNotFoundException;
import com.example.demo.exception.user.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostRecommendAddServiceImplTest {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final PostRecommendRepository postRecommendRepository;
    private final PostRecommendAddService postRecommendAddService;

    @Autowired
    public PostRecommendAddServiceImplTest(UserRepository userRepository, PostRepository postRepository, BoardRepository boardRepository, PostRecommendRepository postRecommendRepository, PostRecommendAddService postRecommendAddService) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.boardRepository = boardRepository;
        this.postRecommendRepository = postRecommendRepository;
        this.postRecommendAddService = postRecommendAddService;
    }

    @Test
    void 게시물추천_정상작동() {
        //given
        User user = new User("test_id", "test_password", "test_name", "test_email", "test_phone");
        userRepository.save(user);
        Long userId = user.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);

        Post post = new Post("test_title", "test_content", user, board);
        postRepository.save(post);
        Long postId = post.getId();

        //when
        postRecommendAddService.add(postId, userId);
        PostRecommend postRecommend = postRecommendRepository.findByUserIdAndPostId(userId, postId).orElseThrow();

        //then
        assertEquals(1, post.getRecommendCount());
        assertEquals("RECOMMEND", postRecommend.getRecommendStatus().name());
        assertEquals(postId, postRecommend.getPost().getId());
        assertEquals("test_title", postRecommend.getPost().getTitle());
        assertEquals("test_content", postRecommend.getPost().getContent());
    }

    @Test
    void 게시물추천_없는게시물_비정상작동() {
        //given
        User user = new User("test_id", "test_password", "test_name", "test_email", "test_phone");
        userRepository.save(user);
        Long userId = user.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);

        Post post = new Post("test_title", "test_content", user, board);
        postRepository.save(post);
        Long postId = post.getId();

        //when
        PostNotFoundException postNotFoundException = assertThrows(PostNotFoundException.class,
                () -> postRecommendAddService.add(postId + 1, userId)
        );

        //then
        assertEquals("This post could not be found", postNotFoundException.getMessage());
    }

    @Test
    void 게시물추천_없는사용자_비정상작동() {
        //given
        User user = new User("test_id", "test_password", "test_name", "test_email", "test_phone");
        userRepository.save(user);
        Long userId = user.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);

        Post post = new Post("test_title", "test_content", user, board);
        postRepository.save(post);
        Long postId = post.getId();

        //when
        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class,
                () -> postRecommendAddService.add(postId, userId + 1)
        );

        //then
        assertEquals("This ID could not be found", userNotFoundException.getMessage());
    }

    @Test
    void 게시물추천_중복추천_비정상작동() {
        //given
        User user = new User("test_id", "test_password", "test_name", "test_email", "test_phone");
        userRepository.save(user);
        Long userId = user.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);

        Post post = new Post("test_title", "test_content", user, board);
        postRepository.save(post);
        Long postId = post.getId();

        PostRecommend postRecommend = new PostRecommend(user, post, RecommendStatus.RECOMMEND);
        postRecommendRepository.save(postRecommend);


        //when
        PostAlreadyRecommendedException postAlreadyRecommendedException = assertThrows(PostAlreadyRecommendedException.class,
                () -> postRecommendAddService.add(postId, userId)
        );

        //then
        assertEquals("The post has already been recommended", postAlreadyRecommendedException.getMessage());
    }
}