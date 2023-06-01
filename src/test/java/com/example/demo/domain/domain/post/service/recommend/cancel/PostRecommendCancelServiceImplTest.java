package com.example.demo.domain.domain.post.service.recommend.cancel;

import com.example.demo.domain.domain.board.domain.Board;
import com.example.demo.domain.domain.board.domain.BoardRepository;
import com.example.demo.domain.domain.post.domain.Post;
import com.example.demo.domain.domain.post.domain.PostRecommend;
import com.example.demo.domain.domain.post.domain.PostRecommendRepository;
import com.example.demo.domain.domain.post.domain.PostRepository;
import com.example.demo.domain.domain.post.service.recommend.update.PostRecommendUpdateService;
import com.example.demo.domain.domain.user.domain.User;
import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.enums.board.BoardStatus;
import com.example.demo.enums.common.RecommendStatus;
import com.example.demo.exception.post.PostNotFoundException;
import com.example.demo.exception.post.PostNotRecommendedException;
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
class PostRecommendCancelServiceImplTest {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final PostRecommendRepository postRecommendRepository;
    private final PostRecommendCancelService postRecommendCancelService;

    @Autowired
    public PostRecommendCancelServiceImplTest(UserRepository userRepository, PostRepository postRepository, BoardRepository boardRepository, PostRecommendRepository postRecommendRepository, PostRecommendCancelService postRecommendCancelService) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.boardRepository = boardRepository;
        this.postRecommendRepository = postRecommendRepository;
        this.postRecommendCancelService = postRecommendCancelService;
    }

    @Test
    void 추천취소_정상작동() {
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
        postRecommendCancelService.cancel(userId, postId);

        //then
        assertEquals("UNRECOMMENDED", postRecommend.getRecommendStatus().name());
    }

    @Test
    void 추천취소_없는사용자_비정상작동() {
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
        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class,
                () -> postRecommendCancelService.cancel(userId + 1, postId)
        );

        //then
        assertEquals("This ID could not be found", userNotFoundException.getMessage());
    }

    @Test
    void 추천취소_없는게시물_비정상작동() {
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
        PostNotFoundException postNotFoundException = assertThrows(PostNotFoundException.class,
                () -> postRecommendCancelService.cancel(userId, postId + 1)
        );

        //then
        assertEquals("This post could not be found", postNotFoundException.getMessage());
    }

    @Test
    void 추천취소_비추천게시물_비정상작동() {
        //given
        User user = new User("test_id", "test_password", "test_name", "test_email", "test_phone");
        userRepository.save(user);
        Long userId = user.getId();

        Board board = new Board("test_board", BoardStatus.OPERATING);
        boardRepository.save(board);

        Post post = new Post("test_title", "test_content", user, board);
        postRepository.save(post);
        Long postId = post.getId();

        PostRecommend postRecommend = new PostRecommend(user, post, RecommendStatus.UNRECOMMENDED);
        postRecommendRepository.save(postRecommend);

        //when
        PostNotRecommendedException postNotRecommendedException = assertThrows(PostNotRecommendedException.class,
                () -> postRecommendCancelService.cancel(userId, postId)
        );

        //then
        assertEquals("The post is not in the recommended state", postNotRecommendedException.getMessage());
    }
}