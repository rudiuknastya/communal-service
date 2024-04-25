package com.example.admin.controller;

import com.example.admin.model.admin.ProfileRequest;
import com.example.admin.model.admin.ProfileResponse;
import com.example.admin.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/profile")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("")
    public ModelAndView getProfilePage() {
        return new ModelAndView("profile/profile");
    }
    @GetMapping("/get")
    public @ResponseBody ProfileResponse getProfile() {
        return adminService.getProfileResponse();
    }

    @PostMapping("")
    public @ResponseBody ResponseEntity<?> updateProfile(@ModelAttribute @Valid ProfileRequest profileRequest) {
        adminService.updateProfile(profileRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
