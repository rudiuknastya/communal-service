package com.example.chairman.repository;

import com.example.chairman.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByChairmanIdAndUserId(Long chairmanId, Long userId);
}
