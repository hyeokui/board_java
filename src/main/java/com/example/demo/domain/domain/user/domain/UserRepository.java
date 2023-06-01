package com.example.demo.domain.domain.user.domain;

import com.example.demo.exception.user.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);

    default void validateUser(Long userId) {
        if (!existsById(userId)) {
            throw new UserNotFoundException();
        }
    }
}
