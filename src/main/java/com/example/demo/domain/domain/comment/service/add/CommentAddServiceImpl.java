package com.example.demo.domain.domain.comment.service.add;

import com.example.demo.domain.domain.comment.domain.Comment;
import com.example.demo.domain.domain.comment.domain.CommentRepository;
import com.example.demo.domain.domain.post.domain.Post;
import com.example.demo.domain.domain.post.domain.PostRepository;
import com.example.demo.domain.domain.user.domain.User;
import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.exception.post.PostNotFoundException;
import com.example.demo.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentAddServiceImpl implements CommentAddService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    public void add(String content, Long userId, Long postId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        Comment comment = new Comment(content, user, post);
        commentRepository.save(comment);
    }
}
