package com.example.chairman.controller;

import com.example.chairman.entity.enums.RequestStatus;
import com.example.chairman.model.registrationRequest.FilterRequest;
import com.example.chairman.model.registrationRequest.TableRegistrationRequestResponse;
import com.example.chairman.service.RegistrationRequestService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("requests")
public class RegistrationRequestController {
    private final RegistrationRequestService registrationRequestService;

    public RegistrationRequestController(RegistrationRequestService registrationRequestService) {
        this.registrationRequestService = registrationRequestService;
    }
    @GetMapping("")
    public ModelAndView getRegistrationRequestPage(){
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
}
