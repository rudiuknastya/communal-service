package com.example.user.controller;

import com.example.user.entity.User;
import com.example.user.model.invoice.FilterRequest;
import com.example.user.model.invoice.TableInvoiceResponse;
import com.example.user.repository.UserRepository;
import com.example.user.service.InvoiceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("")
    public ModelAndView getInvoicesPage(){
        return new ModelAndView("invoices/invoices");
    }
    @GetMapping("/get")
    public @ResponseBody Page<TableInvoiceResponse> getInvoices(FilterRequest filterRequest){
        return invoiceService.getInvoiceResponsesForTable(filterRequest);
    }
}
