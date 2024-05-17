package com.example.chairman.repository;

import com.example.chairman.entity.Chairman;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChairmanRepository extends JpaRepository<Chairman, Long> {
    Optional<Chairman> findByUsernameAndDeletedIsFalse(String username);
}
