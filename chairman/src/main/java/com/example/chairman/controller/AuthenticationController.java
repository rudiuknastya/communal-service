package com.example.chairman.controller;

import com.example.chairman.model.authentication.EmailRequest;
import com.example.chairman.model.authentication.ForgotPasswordRequest;
import com.example.chairman.service.ChairmanPasswordResetTokenService;
import com.example.chairman.service.MailService;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping("/chairman")
public class AuthenticationController {
    private final ChairmanPasswordResetTokenService passwordResetTokenService;
    private final MailService mailService;

    public AuthenticationController(ChairmanPasswordResetTokenService passwordResetTokenService,
                                    MailService mailService) {
        this.passwordResetTokenService = passwordResetTokenService;
        this.mailService = mailService;
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return new ModelAndView("authentication/login");
        } else {
            return new ModelAndView("redirect:users");
        }
    }
    @GetMapping("/forgotPassword")
    public ModelAndView getForgotPasswordPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return new ModelAndView("authentication/forgotPassword");
        } else {
            return new ModelAndView("redirect:users");
        }
    }

    @PostMapping("/forgotPassword")
    public @ResponseBody ResponseEntity<?> sendPasswordResetToken(@Valid EmailRequest emailRequest) {
        String token = passwordResetTokenService.createOrUpdatePasswordResetToken(emailRequest);
        mailService.sendToken(token,emailRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/sentToken")
    public ModelAndView getSentTokenPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return new ModelAndView("authentication/sentToken");
        } else {
            return new ModelAndView("redirect:users");
        }
    }
    @GetMapping("/changePassword")
    public ModelAndView changePassword(@RequestParam("token")String token){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            if (passwordResetTokenService.isPasswordResetTokenValid(token)) {
                ModelAndView modelAndView = new ModelAndView("authentication/changePassword");
                modelAndView.addObject("token", token);
                return modelAndView;
            } else {
                return new ModelAndView("authentication/tokenExpired");
            }
        } else {
            return new ModelAndView("redirect:users");
        }
    }

    @PostMapping("/changePassword")
    public @ResponseBody ResponseEntity<?> setNewPassword(@RequestParam("token")String token,
                                                          @Valid @ModelAttribute ForgotPasswordRequest forgotPasswordRequest){
        if(passwordResetTokenService.isPasswordResetTokenValid(token)){
            passwordResetTokenService.updatePassword(token, forgotPasswordRequest.password());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @GetMapping("/success")
    public ModelAndView getSuccessPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return new ModelAndView("authentication/success");
        } else {
            return new ModelAndView("redirect:users");
        }
    }
    @GetMapping("/tokenExpired")
    public ModelAndView getTokenExpiredPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return new ModelAndView("authentication/tokenExpired");
        } else {
            return new ModelAndView("redirect:users");
        }
    }
}
