package com.example.admin.service;

import com.example.admin.model.admin.ProfileRequest;
import com.example.admin.model.admin.ProfileResponse;

public interface AdminService {
    void createAdminIfNotExist();
    ProfileResponse getProfileResponse();
    void updateProfile(ProfileRequest profileRequest);
}
