package com.example.admin.repository;

import com.example.admin.entity.Chairman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChairmanRepository extends JpaRepository<Chairman, Long>, JpaSpecificationExecutor<Chairman> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByUsername(String username);
}
