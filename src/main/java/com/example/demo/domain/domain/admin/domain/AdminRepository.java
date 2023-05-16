package com.example.demo.domain.domain.admin.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    boolean existsByAdminId(String adminId);

    Optional<Admin> findOptionalAdminByAdminId(String adminId);

}
