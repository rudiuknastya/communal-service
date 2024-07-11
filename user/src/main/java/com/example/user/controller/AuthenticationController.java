package com.example.user.controller;

import com.example.user.model.authentication.EmailRequest;
import com.example.user.model.authentication.ForgotPasswordRequest;
import com.example.user.model.authentication.RegisterRequest;
import com.example.user.model.general.SelectSearchRequest;
import com.example.user.model.house.HouseNumberResponse;
import com.example.user.service.HouseService;
import com.example.user.service.MailService;
import com.example.user.service.PasswordResetTokenService;
import com.example.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthenticationController {
    private final PasswordResetTokenService passwordResetTokenService;
    private final UserService userService;
    private final HouseService houseService;
    private final MailService mailService;

    public AuthenticationController(PasswordResetTokenService passwordResetTokenService,
                                    UserService userService, HouseService houseService,
                                    MailService mailService) {
        this.passwordResetTokenService = passwordResetTokenService;
        this.userService = userService;
        this.houseService = houseService;
        this.mailService = mailService;
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return new ModelAndView("authentication/login");
        } else {
            return new ModelAndView("redirect:invoices");
        }
    }
    @GetMapping("/forgotPassword")
    public ModelAndView getForgotPasswordPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return new ModelAndView("authentication/forgotPassword");
        } else {
            return new ModelAndView("redirect:invoices");
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
            return new ModelAndView("redirect:invoices");
        }
    }
    @GetMapping("/changePassword")
    public ModelAndView getChangePasswordPage(@RequestParam("token")String token){
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
            return new ModelAndView("redirect:invoices");
        }
    }

    @PostMapping("/changePassword")
    public @ResponseBody ResponseEntity<?> changePassword(@RequestParam("token")String token,
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
            return new ModelAndView("redirect:invoices");
        }
    }
    @GetMapping("/tokenExpired")
    public ModelAndView getTokenExpiredPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return new ModelAndView("authentication/tokenExpired");
        } else {
            return new ModelAndView("redirect:invoices");
        }
    }
    @GetMapping("/register")
    public ModelAndView getRegisterPage() {
        return new ModelAndView("authentication/registration");
    }
    @PostMapping("/register")
    public @ResponseBody ResponseEntity<?> register(@Valid @ModelAttribute RegisterRequest registerRequest){
        userService.register(registerRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/register/success")
    public ModelAndView getRegisterSuccessPage() {
        return new ModelAndView("authentication/registration-success");
    }
    @GetMapping("/register/get-cities")
    public @ResponseBody Page<String> getHouseCitiesForRegistration(SelectSearchRequest selectSearchRequest) {
        return houseService.getCitiesForSelect(selectSearchRequest);
    }
    @GetMapping("/register/get-streets")
    public @ResponseBody Page<String> getHouseStreetsForRegistration(SelectSearchRequest selectSearchRequest,
                                                                @RequestParam("city") String city,
                                                                @RequestParam(name = "number", required = false) String number) {
        return houseService.getStreetsForSelect(selectSearchRequest, city, number);
    }
    @GetMapping("/register/get-numbers")
    public @ResponseBody Page<HouseNumberResponse> getHouseNumbersForRegistration(SelectSearchRequest selectSearchRequest,
                                                             @RequestParam("city") String city,
                                                             @RequestParam(name = "street", required = false) String street){
        return houseService.getNumbersForSelect(selectSearchRequest, city, street);
    }
}
