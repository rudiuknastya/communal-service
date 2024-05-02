package com.example.admin.controller;

import com.example.admin.entity.UserStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    @GetMapping("")
    public ModelAndView getUsersPage(){
        return new ModelAndView("users/users");
    }
    @GetMapping("/new")
    public ModelAndView getCreateUserPage(){
        return new ModelAndView("users/create-user");
    }
    @GetMapping("/get-statuses")
    public @ResponseBody UserStatus[] getStatuses(){
        return UserStatus.values();
    }

}
