package com.example.chairman.service;

import com.example.chairman.model.chat.PageRequest;
import com.example.chairman.model.chat.UserResponse;
import org.springframework.data.domain.Page;

public interface ChatService {
    Page<UserResponse> getUsersForChat(PageRequest pageRequest);
}
