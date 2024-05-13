package com.example.admin.service;

import com.example.admin.model.user.*;
import org.springframework.data.domain.Page;

public interface UserService {
    void createUser(CreateUserRequest createUserRequest);
    Page<TableUserResponse> getUserResponsesForTable(FilterRequest filterRequest);
    void deleteUser(Long id);
    UserResponse getUserResponse(Long id);
    void updateUser(Long id, EditUserRequest editUserRequest);
    void importDataFromXlsx(XlsxFileRequest xlsxFileRequest);
}
