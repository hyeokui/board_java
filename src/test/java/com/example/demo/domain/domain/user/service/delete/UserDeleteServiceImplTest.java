package com.example.demo.domain.domain.user.service.delete;

import com.example.demo.domain.domain.user.domain.User;
import com.example.demo.domain.domain.user.domain.UserRepository;
import com.example.demo.enums.user.UserStatus;
import com.example.demo.exception.user.InvalidPasswordException;
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
class UserDeleteServiceImplTest {

    private final UserRepository userRepository;
    private final UserDeleteService userDeleteService;

    @Autowired
    public UserDeleteServiceImplTest(UserRepository userRepository, UserDeleteService userDeleteService) {
        this.userRepository = userRepository;
        this.userDeleteService = userDeleteService;
    }

    @Test
    void 유저삭제_정상작동() {
        //given
        User user = new User("test_id", "test_password", "test_name", "test_email", "test_phone");
        userRepository.save(user);
        Long userId = user.getId();

        //when
        userDeleteService.delete(userId, user.getPassword());

        //then
        assertNotNull(user);
        assertEquals("DELETED", user.getUserStatus().name());
    }

    @Test
    void 유저삭제_비정상작동_아이디없음() {
        //given
        User user = new User("test_id", "test_password", "test_name", "test_email", "test_phone");
        userRepository.save(user);
        Long userId = user.getId();

        //when
        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class, () ->
                userDeleteService.delete(userId + 1, user.getPassword())
        );

        //then
        assertEquals("This ID could not be found", userNotFoundException.getMessage());
    }

    @Test
    void 유저삭제_비정상작동_비밀번호다름() {
        //given
        User user = new User("test_id", "test_password", "test_name", "test_email", "test_phone");
        userRepository.save(user);
        Long userId = user.getId();

        //when
        InvalidPasswordException invalidPasswordException = assertThrows(InvalidPasswordException.class, () ->
                userDeleteService.delete(userId, "wrong_password")
        );

        //then
        assertEquals("Invalid password", invalidPasswordException.getMessage());
    }
}