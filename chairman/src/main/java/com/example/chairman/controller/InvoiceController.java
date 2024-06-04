package com.example.chairman.controller;

import com.example.chairman.model.general.SelectSearchRequest;
import com.example.chairman.model.invoice.*;
import com.example.chairman.service.InvoiceService;
import com.example.chairman.service.UserService;
import com.example.chairman.validation.general.groups.Create;
import com.example.chairman.validation.general.groups.Edit;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("chairman/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final UserService userService;

    public InvoiceController(InvoiceService invoiceService, UserService userService) {
        this.invoiceService = invoiceService;
        this.userService = userService;
    }
    @GetMapping("")
    public ModelAndView getInvoicesPage(){
        return new ModelAndView("invoices/invoices");
    }
    @GetMapping("/get")
    public @ResponseBody Page<TableInvoiceResponse> getInvoices(FilterRequest filterRequest){
        return invoiceService.getTableInvoiceResponses(filterRequest);
    }

    @GetMapping("/new")
    public ModelAndView getCreateInvoicePage(){
        return new ModelAndView("invoices/create-invoice");
    }
    @GetMapping("/get-users")
    public @ResponseBody Page<UserNameResponse> getUsers(SelectSearchRequest selectSearchRequest){
        return userService.getUserNameResponses(selectSearchRequest);
    }
    @GetMapping("/get-number")
    public @ResponseBody String getNumber(){
        return invoiceService.getNumber();
    }
    @GetMapping("/get-personal-account/{id}")
    public @ResponseBody String getUserPersonalAccount(@PathVariable Long id){
        return userService.getPersonalAccount(id);
    }
    @PostMapping("/new")
    public @ResponseBody ResponseEntity<?> createInvoice(@Validated(Create.class) @ModelAttribute
                                                             InvoiceRequest invoiceRequest){
        invoiceService.createInvoice(invoiceRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/delete-invoices")
    public @ResponseBody ResponseEntity<?> deleteInvoices(@RequestParam(name = "invoiceIds[]", required = false) Long[] invoiceIds) {
        invoiceService.deleteInvoices(invoiceIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/edit/{id}")
    public ModelAndView getEditInvoicePage(){
        return new ModelAndView("invoices/edit-invoice");
    }
    @GetMapping("/edit/get/{id}")
    public @ResponseBody InvoiceResponse getInvoice(@PathVariable Long id){
        return invoiceService.getInvoiceResponse(id);
    }
    @PostMapping("/edit/{id}")
    public @ResponseBody ResponseEntity<?> updateInvoice(@PathVariable Long id,
                                                         @Validated(Edit.class) @ModelAttribute
                                                         InvoiceRequest invoiceRequest){
        invoiceService.updateInvoice(id, invoiceRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
