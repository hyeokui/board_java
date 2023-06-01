package com.example.demo.domain.domain.post.service.user.delete;

import com.example.demo.domain.domain.admin.domain.Admin;
import com.example.demo.domain.domain.admin.service.permission.PermissionCheckService;
import com.example.demo.domain.domain.board.service.status.OperatingStatusService;
import com.example.demo.domain.domain.post.domain.PostRepository;
import com.example.demo.domain.domain.user.domain.User;
import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.enums.post.PostStatus;
import com.example.demo.exception.admin.AdminNotFoundException;
import com.example.demo.exception.post.PostNotFoundException;
import com.example.demo.exception.user.InvalidPasswordException;
import com.example.demo.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserPostDeleteServiceImpl implements UserPostDeleteService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final OperatingStatusService operatingStatusService;

    @Override
    public void delete(Long postId, Long userId, String password, String boardName) {
        foundIdAndCheckPassword(userId, password);
        postRepository.findByIdAndUserIdAndPostStatus(postId, userId, PostStatus.ACTIVE).ifPresentOrElse(post -> {
                    operatingStatusService.isBoardOperating(boardName);
                    post.delete();
                },
                () ->
                {
                    throw new PostNotFoundException();
                }
        );
    }

    private void foundIdAndCheckPassword(Long userId, String password) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException();
        }
    }
}
