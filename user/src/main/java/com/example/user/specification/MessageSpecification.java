package com.example.user.specification;


import com.example.user.entity.Message;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public interface MessageSpecification {
    static Specification<Message> byDeleted(){
        return (root, query, builder) ->
                builder.equal(root.get("deleted"), false);
    }
    static Specification<Message> bySubjectLike(String subject){
        return (root, query, builder) ->
                builder.like(builder.upper(root.get("subject")), "%"+subject.toUpperCase()+"%");
    }
    static Specification<Message> byCreationDateGreaterThanOrEqual(LocalDate dateFrom){
        return (root, query, builder) ->
                builder.greaterThanOrEqualTo(builder.function("date", LocalDate.class,root.get("creationDate")), dateFrom);
    }
    static Specification<Message> byCreationDateLessThanOrEqual(LocalDate dateTo){
        return (root, query, builder) ->
                builder.lessThanOrEqualTo(builder.function("date", LocalDate.class,root.get("creationDate")), dateTo);
    }

}
