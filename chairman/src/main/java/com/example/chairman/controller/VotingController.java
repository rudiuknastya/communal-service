package com.example.chairman.controller;

import com.example.chairman.entity.UserVote;
import com.example.chairman.entity.VotingResultStatus;
import com.example.chairman.entity.VotingStatus;
import com.example.chairman.model.voting.*;
import com.example.chairman.service.VotingService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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
    @GetMapping("")
    public ModelAndView getVotingFormsPage(){
        return new ModelAndView("voting/voting-forms");
    }
    @GetMapping("/get")
    public @ResponseBody Page<TableVotingFormResponse> getVotingForms(FilterRequest filterRequest){
        return votingService.getVotingFormResponsesForTable(filterRequest);
    }
    @GetMapping("/new")
    public ModelAndView getCreateVotingFormPage(){
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
    @GetMapping("/get-resultStatuses")
    public @ResponseBody VotingResultStatus[] getResultStatuses(){
        return VotingResultStatus.values();
    }
    @GetMapping("/edit/{id}")
    public ModelAndView getEditVotingFormPage(){
        return new ModelAndView("voting/edit-voting-form");
    }
    @GetMapping("/edit/get/{id}")
    public @ResponseBody VotingFormDto getVotingFormForEDIT(@PathVariable Long id){
        return votingService.getVotingFormDto(id);
    }
    @PostMapping("/edit/{id}")
    public @ResponseBody ResponseEntity<?> getEditVotingFormPage(@PathVariable Long id,
                                                                 @Valid @ModelAttribute
                                                                 VotingFormDto votingFormDto){
        votingService.updateVotingForm(votingFormDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ModelAndView getVotingFormForViewPage(){
        return new ModelAndView("voting/view-voting-form");
    }

    @GetMapping("get-for-view/{id}")
    public @ResponseBody ViewVotingFormResponse getVotingFormForView(@PathVariable Long id){
        return votingService.getViewVotingFormResponse(id);
    }
    @GetMapping("users/{id}")
    public ModelAndView getVotedUsersPage(){
        return new ModelAndView("voting/voted-users");
    }

    @GetMapping("users/get/{id}")
    public @ResponseBody Page<VotedUserResponse> getVotedUsers(@PathVariable Long id, UsersFilterRequest usersFilterRequest){
        return votingService.getVotedUserResponsesForTable(id, usersFilterRequest);
    }
    @GetMapping("users/get-userVotes")
    public @ResponseBody UserVote[] getUserVotes(){
        return UserVote.values();
    }
    @DeleteMapping("delete/{id}")
    public @ResponseBody ResponseEntity<?> deleteVotingForm(@PathVariable Long id){
        votingService.deleteVotingForm(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
