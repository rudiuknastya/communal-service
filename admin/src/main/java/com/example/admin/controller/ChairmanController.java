package com.example.admin.controller;

import com.example.admin.entity.enums.ChairmanStatus;
import com.example.admin.model.chairmen.*;
import com.example.admin.service.ChairmanService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/chairmen")
public class ChairmanController {
    private final ChairmanService chairmanService;

    public ChairmanController(ChairmanService chairmanService) {
        this.chairmanService = chairmanService;
    }

    @GetMapping("/new")
    public ModelAndView getCreateChairmanPage(){
        return new ModelAndView("chairmen/create-chairman");
    }
    @PostMapping("/new")
    public @ResponseBody ResponseEntity<?> saveChairman(@Valid @ModelAttribute
                                                            CreateChairmanRequest createChairmanRequest){
        chairmanService.createChairman(createChairmanRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/get-statuses")
    public @ResponseBody ChairmanStatus[] getStatuses(){
        return ChairmanStatus.values();
    }
    @GetMapping("")
    public ModelAndView getCreateChairmen(){
        return new ModelAndView("chairmen/chairmen");
    }
    @GetMapping("/get")
    public @ResponseBody Page<TableChairmanResponse> getChairmen(FilterRequest request){
        return chairmanService.getChairmenForTable(request);
    }
    @GetMapping("/check-delete/{id}")
    public @ResponseBody boolean checkIfPossibleToDelete(@PathVariable Long id){
        return chairmanService.checkIfPossibleToDelete(id);
    }
    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<?> deleteChairman(@PathVariable Long id){
        chairmanService.deleteChairman(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/edit/{id}")
    public ModelAndView getEditChairmanPage(){
        return new ModelAndView("chairmen/edit-chairman");
    }

    @GetMapping("/edit/get/{id}")
    public @ResponseBody ChairmanResponse getChairman(@PathVariable Long id){
        return chairmanService.getChairmanResponse(id);
    }
    @PostMapping("/edit/{id}")
    public @ResponseBody ResponseEntity<?> updateChairman(@PathVariable Long id,
                                                          @Valid @ModelAttribute EditChairmanRequest editChairmanRequest) {
        chairmanService.updateChairman(editChairmanRequest, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
