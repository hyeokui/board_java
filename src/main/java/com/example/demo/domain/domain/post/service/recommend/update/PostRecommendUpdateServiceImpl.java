package com.example.demo.domain.domain.post.service.recommend.update;

import com.example.demo.domain.domain.post.domain.Post;
import com.example.demo.domain.domain.post.domain.PostRecommendRepository;
import com.example.demo.domain.domain.post.domain.PostRepository;
import com.example.demo.enums.common.RecommendStatus;
import com.example.demo.exception.post.PostAlreadyRecommendedException;
import com.example.demo.exception.post.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostRecommendUpdateServiceImpl implements PostRecommendUpdateService {

    private final PostRepository postRepository;
    private final PostRecommendRepository postRecommendRepository;

    @Override
    public void update(Long userId, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
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
