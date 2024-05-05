package com.example.admin.controller;

import com.example.admin.entity.Admin;
import com.example.admin.model.admin.MFATokenResponse;
import com.example.admin.model.admin.ProfileRequest;
import com.example.admin.model.admin.ProfileResponse;
import com.example.admin.service.AdminService;
import com.example.admin.service.MFATokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/profile")
public class AdminController {
    private final AdminService adminService;
    private  final MFATokenService mfaTokenService;

    public AdminController(AdminService adminService, MFATokenService mfaTokenService) {
        this.adminService = adminService;
        this.mfaTokenService = mfaTokenService;
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
    @GetMapping("/getQR")
    public @ResponseBody MFATokenResponse getQRCode() {
        return mfaTokenService.getMFATokenResponse();
    }
    @PostMapping("/save-secret-key")
    public @ResponseBody ResponseEntity<?> saveSecretKey(@RequestParam(name = "secretKey") String secretKey) {
        adminService.saveSecretKey(secretKey);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/verify-code")
    public @ResponseBody ResponseEntity<?> verifyCode(@RequestParam(name = "code") String code) {
        Admin admin = adminService.getAuthenticatedAdmin();
        if(mfaTokenService.verifyTotp(code, admin.getSecret())) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/disable-faAuthentication")
    public @ResponseBody ResponseEntity<?> disableFaAuthentication(@RequestParam(name = "code") String code) {
        Admin admin = adminService.getAuthenticatedAdmin();
        if(mfaTokenService.verifyTotp(code, admin.getSecret())) {
            adminService.disableFaAuthentication();
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
