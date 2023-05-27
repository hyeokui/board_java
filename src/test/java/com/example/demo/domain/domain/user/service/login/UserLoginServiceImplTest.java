package com.example.demo.domain.domain.user.service.login;

import com.example.demo.domain.domain.user.domain.User;
import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.exception.user.InvalidPasswordException;
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
class UserLoginServiceImplTest {

    private final UserRepository userRepository;
    private final UserLoginService userLoginService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserLoginServiceImplTest(UserRepository userRepository, UserLoginService userLoginService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userLoginService = userLoginService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Test
    void 유저로그인_정상작동() {
        //given
        User user = new User("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", "test_phone");
        userRepository.save(user);

        //when
        Long loginId = userLoginService.login(user.getUserId(), "test_password");

        //then
        assertEquals(user.getId(), loginId);
    }

    @Test
    void 유저로그인_비정상작동_아이디없음() {
        //given
        User user = new User("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", "test_phone");
        userRepository.save(user);

        //when
        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class, () ->
                userLoginService.login("wrong_id", user.getPassword())
        );

        //then
        assertEquals("This ID could not be found", userNotFoundException.getMessage());
    }

    @Test
    void 유저로그인_비정상작동_비밀번호다름() {
        //given
        User user = new User("test_id", bCryptPasswordEncoder.encode("test_password"), "test_name", "test_email", "test_phone");
        userRepository.save(user);

        //when
        InvalidPasswordException invalidPasswordException = assertThrows(InvalidPasswordException.class, () ->
                userLoginService.login(user.getUserId(), user.getPassword() + 1)
        );

        //then
        assertEquals("Invalid password", invalidPasswordException.getMessage());
    }
}