package com.example.demo.domain.domain.comment.service.delete;

import com.example.demo.domain.domain.comment.domain.CommentRepository;
import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.exception.comment.CommentNotFoundException;
import com.example.demo.exception.user.InvalidPasswordException;
import com.example.demo.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentDeleteServiceImpl implements CommentDeleteService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public void delete(Long userId, Long commentId, String password) {
        commentRepository.findByIdAndUserId(commentId, userId).ifPresentOrElse(comment -> {
                    checkPassword(userId, password);
                    comment.delete();
                    commentRepository.save(comment);
                },
                () -> {
                    throw new CommentNotFoundException();
                }
        );
    }

    private void checkPassword(Long userId, String password) {
        userRepository.findById(userId).ifPresentOrElse(user -> {
                    if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
                        throw new InvalidPasswordException();
                    }
                },
                () -> {
                    throw new UserNotFoundException();
                }
        );
    }
}
