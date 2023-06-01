package com.example.demo.domain.domain.post.service.recommend.add;

import com.example.demo.domain.domain.post.domain.Post;
import com.example.demo.domain.domain.post.domain.PostRecommend;
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
public class PostRecommendAddServiceImpl implements PostRecommendAddService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostRecommendRepository postRecommendRepository;

    @Override
    public void add(Long postId, Long userId) {
        Post post = postRepository.validatePost(postId);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        checkDuplicateUserRecommendation(userId, postId);

        PostRecommend postRecommend = new PostRecommend(user, postRepository.validatePost(postId), RecommendStatus.RECOMMEND);
        postRecommendRepository.save(postRecommend);

        post.increaseRecommendCount();
        postRepository.save(post);
    }

    public void checkDuplicateUserRecommendation(Long userId, Long postId) {
        if (postRecommendRepository.existsByUserIdAndPostIdAndRecommendStatus(userId, postId, RecommendStatus.RECOMMEND)) {
            throw new PostAlreadyRecommendedException();
        }
    }
}
