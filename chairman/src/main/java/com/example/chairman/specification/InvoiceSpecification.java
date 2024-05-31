package com.example.chairman.specification;

import com.example.chairman.entity.Invoice;
import com.example.chairman.entity.User;
import jakarta.persistence.criteria.Join;
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
    static Specification<Invoice> byUserPersonalAccount(String personalAccount) {
        return (root, query, builder) -> {
            Join<Invoice, User> userJoin = root.join("user");
            return builder.like(builder.upper(userJoin.get("personalAccount")), "%" + personalAccount.toUpperCase() + "%");
        };
    }
    static Specification<Invoice> byCreationDateGreaterOrEqual(LocalDate creationDateFrom){
        return (root, query, builder) ->
                builder.greaterThanOrEqualTo(root.get("creationDate"), creationDateFrom);
    }
    static Specification<Invoice> byCreationDateLessOrEqual(LocalDate creationDateTo){
        return (root, query, builder) ->
                builder.lessThanOrEqualTo(root.get("creationDate"), creationDateTo);
    }
}
