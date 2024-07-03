package com.example.admin.controller;

import com.example.admin.entity.enums.UserStatus;
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

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {
    private final HouseService houseService;
    private final UserService userService;

    public UserController(HouseService houseService, UserService userService) {
        this.houseService = houseService;
        this.userService = userService;
    }

    @GetMapping("")
    public ModelAndView getUsersPage(){
        Map<String, String> attributes = new HashMap<>();
        attributes.put("getUrl","users/get");
        attributes.put("deleteUrl","users/delete/");
        attributes.put("citySelectUrl","users/get-cities");
        attributes.put("streetSelectUrl","users/get-streets");
        attributes.put("numberSelectUrl","users/get-numbers");
        attributes.put("statusSelectUrl","users/get-statuses");
        attributes.put("uploadFileUrl","users/upload-file");
        return new ModelAndView("users/users", attributes);
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
                                                 @RequestParam(name = "number", required = false) String number){
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
    @PostMapping("/upload-file")
    public @ResponseBody ResponseEntity<?> uploadXlsxFile(@Valid @ModelAttribute XlsxFileRequest xlsxFileRequest){
        userService.importDataFromXlsx(xlsxFileRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/filter-by-house/{id}")
    public ModelAndView getUsersPageWithFilterByHouse(){
        Map<String, String> attributes = new HashMap<>();
        attributes.put("getUrl","../get");
        attributes.put("deleteUrl","../delete/");
        attributes.put("citySelectUrl","../get-cities");
        attributes.put("streetSelectUrl","../get-streets");
        attributes.put("numberSelectUrl","../get-numbers");
        attributes.put("statusSelectUrl","../get-statuses");
        attributes.put("uploadFileUrl","../upload-file");
        return new ModelAndView("users/users", attributes);
    }
    @GetMapping("/filter-by-house/get/{id}")
    public @ResponseBody FilterHouseResponse getUsersPageWithFilterByHouse(@PathVariable Long id){
        return houseService.getHouseResponseForUsersFilter(id);
    }
}
