package com.example.demo.domain.domain.post.service.recommend.cancel;

import com.example.demo.domain.domain.post.domain.Post;
import com.example.demo.domain.domain.post.domain.PostRecommendRepository;
import com.example.demo.domain.domain.post.domain.PostRepository;
import com.example.demo.exception.post.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostRecommendCancelServiceImpl implements PostRecommendCancelService {

    private final PostRepository postRepository;
    private final PostRecommendRepository postRecommendRepository;

    @Override
    public void cancel(Long userId, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        postRecommendRepository.findByUserIdAndPostId(userId, postId).ifPresent(postRecommend -> {
                    postRecommend.cancel();
                    postRecommendRepository.save(postRecommend);

                    post.decreaseRecommendCount();
                    postRepository.save(post);
                }
        );
    }
}
