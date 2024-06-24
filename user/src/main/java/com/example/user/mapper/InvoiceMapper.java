package com.example.user.mapper;

import com.example.user.entity.Invoice;
import com.example.user.model.invoice.TableInvoiceResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface InvoiceMapper {
    List<TableInvoiceResponse> invoiceListToTableInvoiceResponseList(List<Invoice> invoices);
    @Mapping(target = "id", source = "id")
    TableInvoiceResponse invoiceToTableInvoiceResponse(Invoice invoice);
}
