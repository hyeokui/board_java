package com.example.demo.domain.domain.user.service.update;

import com.example.demo.domain.domain.user.domain.User;
import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.domain.domain.user.service.update.dto.UserUpdateServiceDto;
import com.example.demo.exception.user.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserUpdateServiceImplTest {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final UserUpdateService userUpdateService;

    @Autowired
    public UserUpdateServiceImplTest(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, UserUpdateService userUpdateService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.userUpdateService = userUpdateService;
    }

    @Test
    void 유저업데이트_정상작동() {
        //given
        User user = new User("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", "test_phone");
        userRepository.save(user);
        Long userId = user.getId();

        UserUpdateServiceDto userUpdateServiceDto = new UserUpdateServiceDto("update_name", "update_password", "update_email", "update_phone");

        //when
        userUpdateService.update(userId, userUpdateServiceDto);

        //then
        assertNotNull(user);
        assertEquals("update_name", user.getName());
        assertTrue(bCryptPasswordEncoder.matches("update_password", user.getPassword()));
        assertEquals("update_email", user.getEmail());
        assertEquals("update_phone", user.getPhone());
    }

    @Test
    void 유저업데이트_비정상작동_아이디없음() {
        //given
        User user = new User("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", "test_phone");
        userRepository.save(user);
        Long userId = user.getId();

        UserUpdateServiceDto userUpdateServiceDto = new UserUpdateServiceDto("update_name", "update_password", "update_email", "update_phone");

        //when
        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class, () ->
                userUpdateService.update(userId + 1, userUpdateServiceDto)
        );

        //then
        assertEquals("This ID could not be found", userNotFoundException.getMessage());
    }
}