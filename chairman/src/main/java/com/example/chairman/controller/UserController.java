package com.example.chairman.controller;

import com.example.chairman.entity.UserStatus;
import com.example.chairman.model.user.UserRequest;
import com.example.chairman.model.user.UserResponse;
import com.example.chairman.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/chairman/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ModelAndView getUsersPage(){
        return new ModelAndView("users/users");
    }
    @GetMapping("/edit/{id}")
    public ModelAndView getEditUserPage(){
        return new ModelAndView("users/edit-user");
    }
    @PostMapping("/edit/{id}")
    public @ResponseBody ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @ModelAttribute UserRequest userRequest){
        userService.updateUser(userRequest, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/edit/get/{id}")
    public @ResponseBody UserResponse getUser(@PathVariable Long id){
        return userService.getUserResponse(id);
    }
    @GetMapping("/get-statuses")
    public @ResponseBody UserStatus[] getStatuses(){
        return UserStatus.values();
    }
}
