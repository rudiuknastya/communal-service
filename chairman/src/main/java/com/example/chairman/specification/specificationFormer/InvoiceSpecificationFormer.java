package com.example.chairman.specification.specificationFormer;

import com.example.chairman.entity.Invoice;
import com.example.chairman.model.invoice.FilterRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static com.example.chairman.specification.InvoiceSpecification.*;
@Component
public class InvoiceSpecificationFormer {
    public Specification<Invoice> formTableSpecification(FilterRequest filterRequest){
        Specification<Invoice> invoiceSpecification = Specification.where(byDeleted());
        if (!filterRequest.number().isEmpty()){
            invoiceSpecification = invoiceSpecification.and(byNumber(filterRequest.number()));
        }
        if (!filterRequest.personalAccount().isEmpty()){
            invoiceSpecification = invoiceSpecification.and(byUserPersonalAccount(filterRequest.personalAccount()));
        }
        if (filterRequest.creationDateFrom() != null){
            invoiceSpecification = invoiceSpecification.and(byCreationDateGreaterOrEqual(filterRequest.creationDateFrom()));
        }
        if (filterRequest.creationDateTo() != null){
            invoiceSpecification = invoiceSpecification.and(byCreationDateLessOrEqual(filterRequest.creationDateTo()));
        }
        return invoiceSpecification;
    }
}
