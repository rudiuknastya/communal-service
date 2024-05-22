package com.example.chairman.controller;

import com.example.chairman.entity.VotingStatus;
import com.example.chairman.model.voting.VotingFormDto;
import com.example.chairman.service.VotingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("chairman/voting")
public class VotingController {
    private final VotingService votingService;

    public VotingController(VotingService votingService) {
        this.votingService = votingService;
    }

    @GetMapping("/new")
    public ModelAndView getVotingFormPage(){
        return new ModelAndView("voting/create-voting-form");
    }
    @PostMapping("/new")
    public @ResponseBody ResponseEntity<?> createVotingForm(@Valid @ModelAttribute VotingFormDto votingFormDto){
        votingService.createVotingForm(votingFormDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/get-statuses")
    public @ResponseBody VotingStatus[] getStatuses(){
        return VotingStatus.values();
    }
}
