package com.example.admin.service;

import com.example.admin.entity.Admin;
import com.example.admin.model.admin.ProfileRequest;
import com.example.admin.model.admin.ProfileResponse;

public interface AdminService {
    void createAdminIfNotExist();
    ProfileResponse getProfileResponse();
    void updateProfile(ProfileRequest profileRequest);
    void saveSecretKey(String secretKey);
    Admin getAuthenticatedAdmin();
    void disableFaAuthentication();
    void updateRole(String role);
}
