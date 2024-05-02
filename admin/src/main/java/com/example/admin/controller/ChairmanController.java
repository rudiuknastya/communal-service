package com.example.admin.controller;

import com.example.admin.entity.Chairman;
import com.example.admin.entity.ChairmanStatus;
import com.example.admin.model.chairmen.CreateChairmanRequest;
import com.example.admin.service.ChairmanService;
import jakarta.validation.Valid;
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
}
