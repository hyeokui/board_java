package com.example.demo.domain.domain.user.service.login;

import com.example.demo.domain.domain.admin.domain.Admin;
import com.example.demo.domain.domain.user.domain.User;
import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.exception.admin.AdminNotFoundException;
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

        return foundIdAndCheckPassword(userId, password).getId();
    }

    private User foundIdAndCheckPassword(String userId, String password) {
        User user = userRepository.findOptionalUserByUserId(userId).orElseThrow(UserNotFoundException::new);

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException();
        }

        return user;
    }
}
