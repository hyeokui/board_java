package com.example.demo.domain.domain.post.service.recommend.update;

import com.example.demo.domain.domain.post.domain.Post;
import com.example.demo.domain.domain.post.domain.PostRecommendRepository;
import com.example.demo.domain.domain.post.domain.PostRepository;
import com.example.demo.domain.domain.user.domain.User;
import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.enums.common.RecommendStatus;
import com.example.demo.exception.post.PostAlreadyRecommendedException;
import com.example.demo.exception.post.PostNotFoundException;
import com.example.demo.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostRecommendUpdateServiceImpl implements PostRecommendUpdateService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostRecommendRepository postRecommendRepository;

    @Override
    public void update(Long userId, Long postId) {
        userRepository.validateUser(userId);
        Post post = postRepository.validatePost(postId);
        postRecommendRepository.findByUserIdAndPostIdAndRecommendStatus(userId, postId, RecommendStatus.UNRECOMMENDED).ifPresentOrElse(postRecommend -> {
                    postRecommend.update();
                    postRecommendRepository.save(postRecommend);

                    post.increaseRecommendCount();
                    postRepository.save(post);
                },
                () -> {
                    throw new PostAlreadyRecommendedException();
                }
        );
    }
}
