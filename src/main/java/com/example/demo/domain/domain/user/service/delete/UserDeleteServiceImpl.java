package com.example.demo.domain.domain.user.service.delete;

import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.exception.user.InvalidPasswordException;
import com.example.demo.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDeleteServiceImpl implements UserDeleteService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void delete(Long userId, String password) {
        userRepository.findById(userId).ifPresentOrElse(user -> {
                    checkPassword(userId, password);
                    user.delete(userId, password);
                },
                () -> {
                    throw new UserNotFoundException();
                }
        );
    }

    private void checkPassword(Long userId, String password) {
        userRepository.findById(userId).ifPresent(user -> {

                    if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
                        throw new InvalidPasswordException();
                    }
                }
        );
    }
}
