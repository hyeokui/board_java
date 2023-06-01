package com.example.demo.domain.domain.user.service.registration;

import com.example.demo.domain.domain.user.domain.User;
import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.domain.domain.user.service.registration.dto.UserRegistrationServiceDto;
import com.example.demo.exception.user.DuplicateUserIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void add(UserRegistrationServiceDto userRegistrationServiceDto) {

        User user = new User(userRegistrationServiceDto.getUserId(),
                bCryptPasswordEncoder.encode(userRegistrationServiceDto.getPassword()),
                userRegistrationServiceDto.getName(),
                userRegistrationServiceDto.getEmail(),
                userRegistrationServiceDto.getPhone());

        checkDuplicationUserId(userRegistrationServiceDto);

        userRepository.save(user);
    }

    private void checkDuplicationUserId(UserRegistrationServiceDto userRegistrationServiceDto) {
        Optional<User> userOptional = userRepository.findByUserId(userRegistrationServiceDto.getUserId());
        if (userOptional.isPresent()) {
            throw new DuplicateUserIdException(userRegistrationServiceDto.getUserId() + "is already exists.");
        }
    }
}
