package com.example.chairman.service;

import com.example.chairman.model.user.FilterRequest;
import com.example.chairman.model.user.TableUserResponse;
import com.example.chairman.model.user.UserRequest;
import com.example.chairman.model.user.UserResponse;
import org.springframework.data.domain.Page;

public interface UserService {
    void updateUser(UserRequest userRequest, Long id);
    UserResponse getUserResponse(Long id);
    Page<TableUserResponse> getUserResponsesForTable(FilterRequest filterRequest);

}
