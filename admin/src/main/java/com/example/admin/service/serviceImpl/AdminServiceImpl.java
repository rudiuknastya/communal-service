package com.example.admin.service.serviceImpl;

import com.example.admin.entity.Admin;
import com.example.admin.mapper.AdminMapper;
import com.example.admin.repository.AdminRepository;
import com.example.admin.service.AdminService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LogManager.getLogger(AdminServiceImpl.class);

    public AdminServiceImpl(AdminRepository adminRepository, AdminMapper adminMapper,
                            PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.adminMapper = adminMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createAdminIfNotExist() {
        logger.info("createAdminIfNotExist() - Creating admin");
        if (tableEmpty()) {
            // todo saving default image
            Admin admin = adminMapper.createFirstAdmin("Адмін", "Адмін",
                    "Адмінович", passwordEncoder.encode("admin"),
                    "admin@gmail.com", "+380991111111", "avatar");
            adminRepository.save(admin);
            logger.info("createAdminIfNotExist() - Admin was created");
        } else {
            logger.info("createAdminIfNotExist() - Admin has already been created");
        }
    }
    private boolean tableEmpty(){
        return adminRepository.count() == 0;
    }
}
