package com.example.chairman.service;

import com.example.chairman.model.invoice.FilterRequest;
import com.example.chairman.model.invoice.InvoiceRequest;
import com.example.chairman.model.invoice.TableInvoiceResponse;
import org.springframework.data.domain.Page;

public interface InvoiceService {
    void createInvoice(InvoiceRequest invoiceRequest);
    String getNumber();
    Page<TableInvoiceResponse> getTableInvoiceResponses(FilterRequest filterRequest);
    void deleteInvoices(Long[] invoiceIds);
}
