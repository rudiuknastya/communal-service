package com.example.chairman.mapper;

import com.example.chairman.entity.Invoice;
import com.example.chairman.entity.User;
import com.example.chairman.model.invoice.InvoiceRequest;
import com.example.chairman.model.invoice.InvoiceResponse;
import com.example.chairman.model.invoice.TableInvoiceResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface InvoiceMapper {
    @Mapping(target = "user", source = "user")
    @Mapping(target = "file", source = "savedFile")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", source = "creationDate")
    @Mapping(target = "number", source = "number")
    Invoice invoiceRequestToInvoice(InvoiceRequest invoiceRequest, User user,
                                    String savedFile, String number, LocalDate creationDate);
    List<TableInvoiceResponse> invoiceListToTableInvoiceResponseList(List<Invoice> invoices);
    @Mapping(target = "personalAccount", source = "user.personalAccount")
    @Mapping(target = "id", source = "id")
    TableInvoiceResponse invoiceToTableInvoiceResponse(Invoice invoice);
    @Mapping(target = "personalAccount", source = "user.personalAccount")
    @Mapping(target = "userNameResponse.id", source = "user.id")
    @Mapping(target = "userNameResponse.fullName", expression = "java(user.getLastName()+\" \"+user.getFirstName()+\" \"+user.getMiddleName())")
    InvoiceResponse invoiceToInvoiceResponse(Invoice invoice);
    @Mapping(target = "user", source = "user")
    @Mapping(target = "file", source = "file")
    @Mapping(target = "id", ignore = true)
    void updateInvoice(@MappingTarget Invoice invoice, User user, String file);
}
