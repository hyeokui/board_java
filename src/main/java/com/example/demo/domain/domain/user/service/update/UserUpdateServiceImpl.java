package com.example.demo.domain.domain.user.service.update;

import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.domain.domain.user.service.update.dto.UserUpdateServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserUpdateServiceImpl implements UserUpdateService {

    private final UserRepository userRepository;

    public void update(Long userId, UserUpdateServiceDto userUpdateServiceDto) {

        userRepository.findById(userId).ifPresent(user ->
                user.update(userUpdateServiceDto.getPassword(),
                        userUpdateServiceDto.getName(),
                        userUpdateServiceDto.getEmail(),
                        userUpdateServiceDto.getPhone())
        );
    }
}
