package com.example.chairman.controller;

import com.example.chairman.model.general.SelectSearchRequest;
import com.example.chairman.model.invoice.FilterRequest;
import com.example.chairman.model.invoice.InvoiceRequest;
import com.example.chairman.model.invoice.TableInvoiceResponse;
import com.example.chairman.model.invoice.UserNameResponse;
import com.example.chairman.service.InvoiceService;
import com.example.chairman.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
    public @ResponseBody ResponseEntity<?> createInvoice(@Valid @ModelAttribute InvoiceRequest invoiceRequest){
        invoiceService.createInvoice(invoiceRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/delete-invoices")
    public @ResponseBody ResponseEntity<?> deleteInvoices(@RequestParam(name = "invoiceIds[]", required = false) Long[] invoiceIds) {
        invoiceService.deleteInvoices(invoiceIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
