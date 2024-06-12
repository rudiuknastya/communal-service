package com.example.user.controller;

import com.example.user.model.user.ProfileResponse;
import com.example.user.service.ProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("profile")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("")
    public ModelAndView getProfilePage(){
        return new ModelAndView("profile/profile");
    }
    @GetMapping("/get")
    public @ResponseBody ProfileResponse getProfile(){
        return profileService.getProfileResponse();
    }
}
