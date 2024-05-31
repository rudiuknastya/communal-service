package com.example.chairman.mapper;

import com.example.chairman.entity.Invoice;
import com.example.chairman.entity.User;
import com.example.chairman.model.invoice.InvoiceRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface InvoiceMapper {
    @Mapping(target = "user", source = "user")
    @Mapping(target = "file", source = "savedFile")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", source = "creationDate")
    @Mapping(target = "number", source = "number")
    Invoice invoiceRequestToInvoice(InvoiceRequest invoiceRequest, User user,
                                    String savedFile, String number, LocalDate creationDate);
}
