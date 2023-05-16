package com.example.demo.domain.domain.user.service.login;

import com.example.demo.domain.domain.user.domain.User;
import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.exception.user.InvalidPasswordException;
import com.example.demo.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Long login(String userId, String password) {

        return checkPassword(userId, bCryptPasswordEncoder.encode(password)).getId();
    }

    private User checkPassword(String userId, String password) {
        Optional<User> userOptional = getUserByUserId(userId)
                .filter(
                        user ->
                                bCryptPasswordEncoder.matches(user.getPassword(), password)
                );

        return userOptional.orElseThrow(InvalidPasswordException::new);
    }

    private Optional<User> getUserByUserId(String userId) {
        if (!userRepository.existsByUserId(userId)) {
            throw new UserNotFoundException();
        }

        return userRepository.findOptionalUserByUserId(userId);
    }
}
