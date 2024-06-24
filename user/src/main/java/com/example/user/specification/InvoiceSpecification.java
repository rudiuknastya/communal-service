package com.example.user.specification;

import com.example.user.entity.Invoice;
import com.example.user.entity.User;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public interface InvoiceSpecification {
    static Specification<Invoice> byDeleted(){
        return (root, query, builder) ->
                builder.equal(root.get("deleted"), false);
    }
    static Specification<Invoice> byNumber(String number){
        return (root, query, builder) ->
                builder.like(builder.upper(root.get("number")), "%"+number.toUpperCase()+"%");
    }
    static Specification<Invoice> byCreationDateGreaterOrEqual(LocalDate creationDateFrom){
        return (root, query, builder) ->
                builder.greaterThanOrEqualTo(root.get("creationDate"), creationDateFrom);
    }
    static Specification<Invoice> byCreationDateLessOrEqual(LocalDate creationDateTo){
        return (root, query, builder) ->
                builder.lessThanOrEqualTo(root.get("creationDate"), creationDateTo);
    }
    static Specification<Invoice> byUser(User user){
        return (root, query, builder) ->
                builder.equal(root.get("user"), user);
    }
}
