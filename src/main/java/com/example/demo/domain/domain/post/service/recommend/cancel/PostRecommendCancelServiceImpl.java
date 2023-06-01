package com.example.demo.domain.domain.post.service.recommend.cancel;

import com.example.demo.domain.domain.post.domain.Post;
import com.example.demo.domain.domain.post.domain.PostRecommendRepository;
import com.example.demo.domain.domain.post.domain.PostRepository;
import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.enums.common.RecommendStatus;
import com.example.demo.exception.post.PostNotFoundException;
import com.example.demo.exception.post.PostNotRecommendedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostRecommendCancelServiceImpl implements PostRecommendCancelService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostRecommendRepository postRecommendRepository;

    @Override
    public void cancel(Long userId, Long postId) {
        userRepository.validateUser(userId);
        Post post = postRepository.validatePost(postId);
        postRecommendRepository.findByUserIdAndPostIdAndRecommendStatus(userId, postId, RecommendStatus.RECOMMEND).ifPresentOrElse(postRecommend -> {
                    postRecommend.cancel();
                    postRecommendRepository.save(postRecommend);

                    post.decreaseRecommendCount();
                    postRepository.save(post);
                },
                () -> {
                    throw new PostNotRecommendedException();
                }
        );
    }
}
