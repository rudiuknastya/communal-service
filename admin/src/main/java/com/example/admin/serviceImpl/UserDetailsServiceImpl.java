package com.example.admin.serviceImpl;

import com.example.admin.entity.Admin;
import com.example.admin.model.admin.AdminDetails;
import com.example.admin.repository.AdminRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AdminRepository adminRepository;
    private final Logger logger = LogManager.getLogger(UserDetailsServiceImpl.class);

    public UserDetailsServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("loadUserByUsername() - Finding admin by email "+username+" for admin details");
        Admin admin = adminRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Admin was not found by email "+username));
        AdminDetails adminDetails = new AdminDetails(admin);
        logger.info("loadUserByUsername() - Admin was found");
        return adminDetails;
    }
}
