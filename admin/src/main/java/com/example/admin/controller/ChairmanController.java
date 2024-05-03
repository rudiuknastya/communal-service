package com.example.admin.controller;

import com.example.admin.entity.ChairmanStatus;
import com.example.admin.model.chairmen.CreateChairmanRequest;
import com.example.admin.model.chairmen.FilterRequest;
import com.example.admin.model.chairmen.TableChairmanResponse;
import com.example.admin.service.ChairmanService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/chairmen")
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
    @GetMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<?> deleteChairman(@PathVariable Long id){
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
