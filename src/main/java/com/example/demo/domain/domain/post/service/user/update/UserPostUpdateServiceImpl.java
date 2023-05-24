package com.example.demo.domain.domain.post.service.user.update;

import com.example.demo.domain.domain.post.domain.PostRepository;
import com.example.demo.exception.post.PostNotFoundException;
import com.example.demo.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserPostUpdateServiceImpl implements UserPostUpdateService {

    private final PostRepository postRepository;

    @Override
    public void update(Long userId, String title, String content) {
        postRepository.findByUserId(userId)
                .orElseThrow(PostNotFoundException::new)
                .update(title, content);
    }
}
