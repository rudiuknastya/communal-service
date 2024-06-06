package com.example.user.repository;

import com.example.user.entity.UserPasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPasswordResetTokenRepository extends JpaRepository<UserPasswordResetToken, Long> {
    Optional<UserPasswordResetToken> findByToken(String token);
}
