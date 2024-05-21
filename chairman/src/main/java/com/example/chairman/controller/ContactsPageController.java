package com.example.chairman.controller;

import com.example.chairman.model.contactsPage.ContactsPageRequest;
import com.example.chairman.model.contactsPage.ContactsPageResponse;
import com.example.chairman.service.ContactsPageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/chairman/contacts")
public class ContactsPageController {
    private final ContactsPageService contactsPageService;

    public ContactsPageController(ContactsPageService contactsPageService) {
        this.contactsPageService = contactsPageService;
    }

    @GetMapping("")
    public ModelAndView getContactsPagePage(){
        return new ModelAndView("contacts/contacts");
    }
    @GetMapping("/get")
    public @ResponseBody ContactsPageResponse getContactsPage(){
        return contactsPageService.getContactsPageResponse();
    }
    @PostMapping("")
    public @ResponseBody ResponseEntity<?> updateContactsPage(@Valid @ModelAttribute
                                                              ContactsPageRequest contactsPageRequest){
        contactsPageService.updateContactsPage(contactsPageRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
