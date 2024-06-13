package com.example.user.controller;

import com.example.user.entity.UserVote;
import com.example.user.entity.VotingResultStatus;
import com.example.user.entity.VotingStatus;
import com.example.user.model.voting.ActiveVotingResponse;
import com.example.user.model.voting.FilterRequest;
import com.example.user.model.voting.TableVotingFormResponse;
import com.example.user.service.VotingService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/voting")
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
    @GetMapping("/get-statuses")
    public @ResponseBody VotingStatus[] getStatuses(){
        return VotingStatus.values();
    }
    @GetMapping("/get-resultStatuses")
    public @ResponseBody VotingResultStatus[] getResultStatuses(){
        return VotingResultStatus.values();
    }
    @GetMapping("/active/{id}")
    public ModelAndView getActiveVotingPage(){
        return new ModelAndView("voting/active-voting");
    }
    @GetMapping("/active/get/{id}")
    public @ResponseBody ActiveVotingResponse getActiveVoting(@PathVariable Long id){
        return votingService.getActiveVotingResponse(id);
    }
    @PostMapping("/active/update-vote/{id}")
    public @ResponseBody ResponseEntity<?> updateVoting(@PathVariable Long id,
                                                        @RequestParam("vote")UserVote vote){
        votingService.updateVote(id, vote);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
