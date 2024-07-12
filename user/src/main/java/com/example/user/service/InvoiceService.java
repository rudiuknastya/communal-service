package com.example.user.service;

import com.example.user.model.invoice.FilterRequest;
import com.example.user.model.invoice.TableInvoiceResponse;
import org.springframework.data.domain.Page;

public interface InvoiceService {
    Page<TableInvoiceResponse> getInvoiceResponsesForTable(FilterRequest filterRequest);
    String getInvoiceFile(Long id);
}
