package com.example.demo.domain.domain.user.service.registration;

import com.example.demo.domain.domain.user.domain.User;
import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.domain.domain.user.service.registration.dto.UserRegistrationServiceDto;
import com.example.demo.exception.user.*;
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

        validateUserRegistrationDto(userRegistrationServiceDto);
        checkDuplicationUserId(userRegistrationServiceDto);
        checkConfirmPassword(userRegistrationServiceDto);

        userRepository.save(user);
    }

    private void checkDuplicationUserId(UserRegistrationServiceDto userRegistrationServiceDto) {
        Optional<User> userOptional = userRepository.findByUserId(userRegistrationServiceDto.getUserId());
        if (userOptional.isPresent()) {
            throw new DuplicateUserIdException(userRegistrationServiceDto.getUserId() + " is already exists");
        }
    }

    private void checkConfirmPassword(UserRegistrationServiceDto userRegistrationServiceDto) {
        if (!userRegistrationServiceDto.getPassword().equals(userRegistrationServiceDto.getConfirmPassword())) {

            throw new PasswordMismatchException();
        }
    }

    private void validateUserRegistrationDto(UserRegistrationServiceDto userRegistrationServiceDto) {
        if (!userRegistrationServiceDto.getUserId().matches("^[a-zA-Z0-9]{6,12}$")) {
            throw new InvalidUserIdException();
        }

        if (!userRegistrationServiceDto.getPassword().matches("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}")) {
            throw new InvalidPasswordException();
        }

        if (!userRegistrationServiceDto.getName().matches("^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$")) {
            throw new InvalidNameException();
        }

        if (!userRegistrationServiceDto.getEmail().matches("^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$")) {
            throw new InvalidEmailException();
        }

        if (!userRegistrationServiceDto.getPhone().matches("\\d{3}\\d{4}\\d{4}")) {
            throw new InvalidPhoneException();
        }
    }
}
