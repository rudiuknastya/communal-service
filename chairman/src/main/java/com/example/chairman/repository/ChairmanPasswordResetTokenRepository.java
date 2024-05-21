package com.example.chairman.repository;

import com.example.chairman.entity.ChairmanPasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChairmanPasswordResetTokenRepository extends JpaRepository<ChairmanPasswordResetToken, Long> {
    Optional<ChairmanPasswordResetToken> findByToken(String token);
}
