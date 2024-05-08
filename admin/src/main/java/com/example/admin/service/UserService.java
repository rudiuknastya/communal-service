package com.example.admin.service;

import com.example.admin.model.user.CreateUserRequest;
import com.example.admin.model.user.FilterRequest;
import com.example.admin.model.user.TableUserResponse;
import org.springframework.data.domain.Page;

public interface UserService {
    void createUser(CreateUserRequest createUserRequest);
    Page<TableUserResponse> getUserResponsesForTable(FilterRequest filterRequest);
    void deleteUser(Long id);
}
