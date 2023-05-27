package com.example.demo.domain.domain.admin.domain;

import com.example.demo.exception.admin.AdminNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    boolean existsByAdminId(String adminId);

    boolean existsById(Long adminId);

    Optional<Admin> findOptionalAdminByAdminId(String adminId);

    default void validateAdmin(Long adminId) {
        findById(adminId).orElseThrow(AdminNotFoundException::new);
    }


}
