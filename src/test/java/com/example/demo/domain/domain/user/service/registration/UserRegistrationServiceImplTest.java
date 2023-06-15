package com.example.demo.domain.domain.user.service.registration;

import com.example.demo.domain.domain.user.domain.User;
import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.domain.domain.user.service.registration.dto.UserRegistrationServiceDto;
import com.example.demo.exception.user.DuplicateUserIdException;
import com.example.demo.exception.user.PasswordMismatchException;
import com.example.demo.exception.user.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRegistrationServiceImplTest {

    private final UserRepository userRepository;
    private final UserRegistrationService userRegistrationService;

    @Autowired
    public UserRegistrationServiceImplTest(UserRepository userRepository, UserRegistrationService userRegistrationService) {
        this.userRepository = userRepository;
        this.userRegistrationService = userRegistrationService;
    }

    @Test
    void 유저회원가입_정상작동() {
        //given
        UserRegistrationServiceDto userRegistrationServiceDto = new UserRegistrationServiceDto("test_id", "test_password", "test_password", "test_name", "test_email", "test_phone");

        //when
        userRegistrationService.add(userRegistrationServiceDto);
        User user = userRepository.findByUserId(userRegistrationServiceDto.getUserId()).orElseThrow(UserNotFoundException::new);

        //then
        assertNotNull(user);
        assertEquals("test_id", user.getUserId());
        assertNotEquals("test_password", user.getPassword());
        assertEquals("test_name", user.getName());
        assertEquals("test_email", user.getEmail());
        assertEquals("test_phone", user.getPhone());
        assertEquals("test_id", user.getUserId());
    }

    @Test
    void 유저회원가입_비정상작동_아이디중복() {
        //given
        User user = new User("test_id", "test_password", "test_name", "test_email", "test_phone");
        userRepository.save(user);

        UserRegistrationServiceDto userRegistrationServiceDto = new UserRegistrationServiceDto("test_id", "test_password", "test_password", "test_name", "test_email", "test_phone");

        //when
        DuplicateUserIdException duplicateUserIdException = assertThrows(DuplicateUserIdException.class, () ->
                userRegistrationService.add(userRegistrationServiceDto)
        );

        //then
        assertEquals(userRegistrationServiceDto.getUserId() + "is already exists.", duplicateUserIdException.getMessage());
    }

    @Test
    void 유저회원가입_비정상작동_패스워드확인불일치() {
        //given
        UserRegistrationServiceDto userRegistrationServiceDto = new UserRegistrationServiceDto("test_id", "test_password", "not_confirm", "test_name", "test_email", "test_phone");

        //when
        PasswordMismatchException passwordMismatchException = assertThrows(PasswordMismatchException.class, () ->
                userRegistrationService.add(userRegistrationServiceDto)
        );

        //then
        assertEquals("passwords do not match", passwordMismatchException.getMessage());
    }

}