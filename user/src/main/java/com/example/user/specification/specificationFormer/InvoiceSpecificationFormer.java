package com.example.user.specification.specificationFormer;

import com.example.user.entity.Invoice;
import com.example.user.entity.User;
import com.example.user.model.invoice.FilterRequest;
import org.springframework.data.jpa.domain.Specification;

import static com.example.user.specification.InvoiceSpecification.*;

public class InvoiceSpecificationFormer {
    public static Specification<Invoice> formSpecification(FilterRequest filterRequest, User user){
        Specification<Invoice> invoiceSpecification = Specification.where(byDeleted()).and(byUser(user));
        if (!filterRequest.number().isEmpty()){
            invoiceSpecification = invoiceSpecification.and(byNumber(filterRequest.number()));
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
