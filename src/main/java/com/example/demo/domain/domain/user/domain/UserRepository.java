package com.example.demo.domain.domain.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOptionalUserByUserId(String userId);

    boolean existsByUserId(String userId);


}
