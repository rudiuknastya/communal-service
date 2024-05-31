package com.example.chairman.service;

import com.example.chairman.model.invoice.InvoiceRequest;

public interface InvoiceService {
    void createInvoice(InvoiceRequest invoiceRequest);
    String getNumber();
}
