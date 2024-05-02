package com.example.admin.serviceImpl;

import com.example.admin.entity.Admin;
import com.example.admin.mapper.AdminMapper;
import com.example.admin.model.admin.AdminDetails;
import com.example.admin.model.admin.ProfileRequest;
import com.example.admin.model.admin.ProfileResponse;
import com.example.admin.repository.AdminRepository;
import com.example.admin.service.AdminService;
import com.example.admin.util.UploadFileUtil;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;
    private final UploadFileUtil uploadFileUtil;
    private final Logger logger = LogManager.getLogger(AdminServiceImpl.class);

    public AdminServiceImpl(AdminRepository adminRepository, AdminMapper adminMapper,
                            PasswordEncoder passwordEncoder, UploadFileUtil uploadFileUtil) {
        this.adminRepository = adminRepository;
        this.adminMapper = adminMapper;
        this.passwordEncoder = passwordEncoder;
        this.uploadFileUtil = uploadFileUtil;
    }

    @Override
    public void createAdminIfNotExist() {
        logger.info("createAdminIfNotExist() - Creating admin");
        if (tableEmpty()) {
            String avatar = uploadFileUtil.saveDefaultAvatar();
            Admin admin = adminMapper.createFirstAdmin("Адмін", "Адмін",
                    "Адмінович", passwordEncoder.encode("admin"),
                    "admin@gmail.com", "+380991111111", avatar);
            saveAdmin(admin);
            logger.info("createAdminIfNotExist() - Admin was created");
        } else {
            logger.info("createAdminIfNotExist() - Admin has already been created");
        }
    }
    private boolean tableEmpty(){
        return adminRepository.count() == 0;
    }

    @Override
    public ProfileResponse getProfileResponse() {
        logger.info("getProfileResponse() - Getting profile response");
        Admin admin = getAuthenticatedAdmin();
        ProfileResponse profileResponse = adminMapper.adminToProfileResponse(admin);
        logger.info("getProfileResponse() - Profile response has been got");
        return profileResponse;
    }

    @Override
    public void updateProfile(ProfileRequest profileRequest) {
        logger.info("updateProfile() - Updating profile");
        Admin admin = getAuthenticatedAdmin();
        String avatar = updateAvatar(profileRequest.avatar(),admin);
        adminMapper.setAdmin(admin, profileRequest, avatar);
        updateAdminInSecurityContext(admin);
        saveAdmin(admin);
        logger.info("updateProfile() - Profile has been updated");
    }
    private void updateAdminInSecurityContext(Admin admin){
        AdminDetails adminDetails = (AdminDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        adminDetails.setAdmin(admin);
    }

    private String updateAvatar(MultipartFile avatar, Admin admin) {
        String currentAvatar = admin.getAvatar();
        if (avatar.isEmpty()){
            return currentAvatar;
        } else {
            uploadFileUtil.deleteFile(currentAvatar);
            String newAvatar = uploadFileUtil.saveMultipartFile(avatar);
            return newAvatar;
        }
    }

    @Override
    public void saveSecretKey(String secretKey) {
        logger.info("saveSecretKey() - Saving secret key");
        Admin admin = getAuthenticatedAdmin();
        admin.setSecret(secretKey);
        admin.setFaAuthentication(true);
        saveAdmin(admin);
        logger.info("saveSecretKey() - Secret key has been saved");
    }
    @Override
    public void disableFaAuthentication() {
        Admin admin = getAuthenticatedAdmin();
        admin.setFaAuthentication(false);
        admin.setSecret(null);
        saveAdmin(admin);
    }

    @Override
    public Admin getAuthenticatedAdmin() {
        logger.info("getAdminByEmail() - Getting authenticated admin");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Admin admin = adminRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(()-> new EntityNotFoundException("Admin was not found by email "+userDetails.getUsername()));
        logger.info("getAdminByEmail() - Admin has been got");
        return admin;
    }
    private void saveAdmin(Admin admin){
        adminRepository.save(admin);
    }
}
