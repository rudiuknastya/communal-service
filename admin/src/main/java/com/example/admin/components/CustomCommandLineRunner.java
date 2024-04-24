package com.example.admin.components;

import com.example.admin.service.AdminService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CustomCommandLineRunner implements CommandLineRunner {
    private final AdminService adminService;

    public CustomCommandLineRunner(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public void run(String... args) throws Exception {
        adminService.createAdminIfNotExist();
    }
}
