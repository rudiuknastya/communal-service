package com.example.admin.controller;

import com.example.admin.entity.Admin;
import com.example.admin.entity.HouseStatus;
import com.example.admin.service.AdminService;
import com.example.admin.service.MFATokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AuthenticationController {
    private final MFATokenService mfaTokenService;
    private final AdminService adminService;

    public AuthenticationController(MFATokenService mfaTokenService, AdminService adminService) {
        this.mfaTokenService = mfaTokenService;
        this.adminService = adminService;
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return new ModelAndView("authentication/login");
        } else {
            return new ModelAndView("redirect:users");
        }
    }
    @GetMapping("/login/2fa")
    public ModelAndView get2FAPage(@RequestParam(name="error", required = false) String error) {
        return new ModelAndView("authentication/2fa");
    }
    @PostMapping("/login/2fa")
    public @ResponseBody ResponseEntity<?> verifyCode(@RequestParam(name = "code") String code){
        Admin admin = adminService.getAuthenticatedAdmin();
        if(mfaTokenService.verifyTotp(code,admin.getSecret())){
            setAuthority();
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    private void setAuthority() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authorities = new ArrayList<>(auth.getAuthorities());
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), authorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        adminService.updateRole("ROLE_ADMIN");
    }
}
