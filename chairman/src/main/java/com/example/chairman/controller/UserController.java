package com.example.chairman.controller;

import com.example.chairman.entity.UserStatus;
import com.example.chairman.model.general.SelectSearchRequest;
import com.example.chairman.model.user.FilterRequest;
import com.example.chairman.model.user.TableUserResponse;
import com.example.chairman.model.user.UserRequest;
import com.example.chairman.model.user.UserResponse;
import com.example.chairman.service.HouseService;
import com.example.chairman.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/chairman/users")
public class UserController {
    private final UserService userService;
    private final HouseService houseService;

    public UserController(UserService userService, HouseService houseService) {
        this.userService = userService;
        this.houseService = houseService;
    }

    @GetMapping("")
    public ModelAndView getUsersPage(){
        return new ModelAndView("users/users");
    }
    @GetMapping("/get")
    public @ResponseBody Page<TableUserResponse> getUsers(FilterRequest filterRequest){
        return userService.getUserResponsesForTable(filterRequest);
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
    @GetMapping("/get-cities")
    public @ResponseBody Page<String> getCities(SelectSearchRequest selectSearchRequest){
        return houseService.getCities(selectSearchRequest);
    }
    @GetMapping("/get-streets")
    public @ResponseBody Page<String> getStreets(SelectSearchRequest selectSearchRequest,
                                                 @RequestParam("city") String city,
                                                 @RequestParam(name = "number", required = false) String number){
        return houseService.getStreets(selectSearchRequest, city, number);
    }
    @GetMapping("/get-numbers")
    public @ResponseBody Page<String> getNumber(SelectSearchRequest selectSearchRequest,
                                                             @RequestParam("city") String city,
                                                             @RequestParam(name = "street", required = false) String street){
        return houseService.getNumbers(selectSearchRequest, city, street);
    }
}
