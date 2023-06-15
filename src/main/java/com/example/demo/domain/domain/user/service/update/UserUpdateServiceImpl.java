package com.example.demo.domain.domain.user.service.update;

import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.domain.domain.user.service.update.dto.UserUpdateServiceDto;
import com.example.demo.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserUpdateServiceImpl implements UserUpdateService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void update(Long userId, UserUpdateServiceDto userUpdateServiceDto) {

        userRepository.findById(userId).ifPresentOrElse(user ->
                        user.update(userUpdateServiceDto.getName(),
                                bCryptPasswordEncoder.encode(userUpdateServiceDto.getPassword()),
                                userUpdateServiceDto.getEmail(),
                                userUpdateServiceDto.getPhone()),
                () -> {
                    throw new UserNotFoundException();
                }
        );
    }
}
