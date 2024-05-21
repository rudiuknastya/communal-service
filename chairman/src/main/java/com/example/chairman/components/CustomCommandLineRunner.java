package com.example.chairman.components;

import com.example.chairman.service.ContactsPageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CustomCommandLineRunner implements CommandLineRunner {
    private final ContactsPageService contactsPageService;

    public CustomCommandLineRunner(ContactsPageService contactsPageService) {
        this.contactsPageService = contactsPageService;
    }

    @Override
    public void run(String... args) throws Exception {
        contactsPageService.createContactsPageIfNotExist();
    }
}
