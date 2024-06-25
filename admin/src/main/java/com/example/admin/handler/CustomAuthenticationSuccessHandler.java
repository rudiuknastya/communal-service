package com.example.admin.handler;

import com.example.admin.entity.Admin;
import com.example.admin.repository.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final AdminRepository adminRepository;

    public CustomAuthenticationSuccessHandler(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails adminDetails = (UserDetails) authentication.getPrincipal();
        String email = adminDetails.getUsername();
        Admin admin = adminRepository.findByEmail(email).orElseThrow(()-> new EntityNotFoundException("Admin was not found by email "+email));
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (admin.isFaAuthentication()){
            admin.setRole("ROLE_PRE_AUTH_ADMIN");
            authorities.add(new SimpleGrantedAuthority("ROLE_PRE_AUTH_ADMIN"));
            response.sendRedirect(request.getContextPath()+"/login/2fa");
        } else {
            admin.setRole("ROLE_ADMIN");
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            response.sendRedirect(request.getContextPath()+"/users");
        }
        adminRepository.save(admin);
        Authentication newAuth = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), authorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
