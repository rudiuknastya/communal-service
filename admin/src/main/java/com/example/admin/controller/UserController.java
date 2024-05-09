package com.example.admin.controller;

import com.example.admin.entity.UserStatus;
import com.example.admin.model.general.SelectSearchRequest;
import com.example.admin.model.houses.HouseNumberResponse;
import com.example.admin.model.user.*;
import com.example.admin.service.HouseService;
import com.example.admin.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/users")
public class UserController {
    private final HouseService houseService;
    private final UserService userService;

    public UserController(HouseService houseService, UserService userService) {
        this.houseService = houseService;
        this.userService = userService;
    }

    @GetMapping("")
    public ModelAndView getUsersPage(){
        return new ModelAndView("users/users");
    }
    @GetMapping("/new")
    public ModelAndView getCreateUserPage(){
        return new ModelAndView("users/create-user");
    }
    @PostMapping("/new")
    public @ResponseBody ResponseEntity<?> createUser(@Valid @ModelAttribute CreateUserRequest createUserRequest){
        userService.createUser(createUserRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/get-statuses")
    public @ResponseBody UserStatus[] getStatuses(){
        return UserStatus.values();
    }
    @GetMapping("/get-cities")
    public @ResponseBody Page<String> getCities(SelectSearchRequest selectSearchRequest){
        return houseService.getCities(selectSearchRequest);
    }
    @GetMapping("/get-streets")
    public @ResponseBody Page<String> getStreets(SelectSearchRequest selectSearchRequest,
                                                 @RequestParam("city") String city,
                                                 @RequestParam(name = "number", required = false) Long number){
        return houseService.getStreets(selectSearchRequest, city, number);
    }
    @GetMapping("/get-numbers")
    public @ResponseBody Page<HouseNumberResponse> getNumber(SelectSearchRequest selectSearchRequest,
                                                             @RequestParam("city") String city,
                                                             @RequestParam(name = "street", required = false) String street){
        return houseService.getNumbers(selectSearchRequest, city, street);
    }
    @GetMapping("/get")
    public @ResponseBody Page<TableUserResponse> getUsers(FilterRequest filterRequest){
        return userService.getUserResponsesForTable(filterRequest);
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<?> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/edit/{id}")
    public ModelAndView getUserPage(){
        return new ModelAndView("users/edit-user");
    }

    @GetMapping("/edit/get/{id}")
    public @ResponseBody UserResponse getUser(@PathVariable Long id){
        return userService.getUserResponse(id);
    }
    @PostMapping("/edit/{id}")
    public @ResponseBody ResponseEntity<?> updateUser(@PathVariable Long id,
                                                      @Valid @ModelAttribute EditUserRequest editUserRequest){
        userService.updateUser(id, editUserRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
