package com.example.user.controller;

import com.example.user.model.contactsPage.ContactsPageResponse;
import com.example.user.model.user.ProfileResponse;
import com.example.user.service.ContactsPageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("contacts")
public class ContactsPageController {
    private final ContactsPageService contactsPageService;
    public ContactsPageController(ContactsPageService contactsPageService) {
        this.contactsPageService = contactsPageService;
    }
    @GetMapping("")
    public ModelAndView getContactsPagePage(){
        return new ModelAndView("contacts-page/contacts-page");
    }
    @GetMapping("/get")
    public @ResponseBody ContactsPageResponse getContactsPage(){
        return contactsPageService.getContactsPageResponse();
    }
}
