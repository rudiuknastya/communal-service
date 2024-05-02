package com.example.admin.repository;

import com.example.admin.entity.Chairman;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChairmanRepository extends JpaRepository<Chairman, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByUsername(String username);
}
