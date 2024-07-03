package com.example.chairman.controller;

import com.example.chairman.entity.enums.RequestStatus;
import com.example.chairman.model.registrationRequest.FilterRequest;
import com.example.chairman.model.registrationRequest.RegistrationRequestResponse;
import com.example.chairman.model.registrationRequest.TableRegistrationRequestResponse;
import com.example.chairman.service.RegistrationRequestService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("requests")
public class RegistrationRequestController {
    private final RegistrationRequestService registrationRequestService;

    public RegistrationRequestController(RegistrationRequestService registrationRequestService) {
        this.registrationRequestService = registrationRequestService;
    }
    @GetMapping("")
    public ModelAndView getRegistrationRequestsPage(){
        return new ModelAndView("registrationRequest/registration-requests");
    }
    @GetMapping("/get")
    public @ResponseBody Page<TableRegistrationRequestResponse> getRegistrationRequests(FilterRequest filterRequest){
        return registrationRequestService.getRegistrationRequestsForTable(filterRequest);
    }
    @GetMapping("/get-statuses")
    public @ResponseBody RequestStatus[] getRegistrationRequestsStatuses(){
        return RequestStatus.values();
    }
    @GetMapping("/delete-requests")
    public @ResponseBody ResponseEntity<?> deleteRequests(@RequestParam(name = "requestIds[]", required = false) Long[] requestIds) {
        registrationRequestService.deleteRequestsByIds(requestIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ModelAndView getRegistrationRequestPage(){
        return new ModelAndView("registrationRequest/registration-request");
    }
    @GetMapping("/get/{id}")
    public @ResponseBody RegistrationRequestResponse getRequest(@PathVariable Long id) {
        return registrationRequestService.getRegistrationRequest(id);
    }
    @PostMapping("/accept/{id}")
    public @ResponseBody  ResponseEntity<?> acceptRequest(@PathVariable Long id) {
        registrationRequestService.acceptRegistrationRequest(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/reject/{id}")
    public @ResponseBody  ResponseEntity<?> rejectRequest(@PathVariable Long id) {
        registrationRequestService.rejectRegistrationRequest(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
