package com.example.chairman.specification;

import com.example.chairman.entity.Message;
import com.example.chairman.entity.User;
import jakarta.persistence.criteria.Join;
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
    static Specification<Message> byUserApartmentNumber(Long apartmentNumber){
        return (root, query, builder) -> {
            Join<Message, User> userJoin = root.join("user");
            return builder.equal(userJoin.get("apartmentNumber"), apartmentNumber);
        };
    }
    static Specification<Message> byUserFirstNameLike(String firstName){
        return (root, query, builder) -> {
            Join<Message, User> userJoin = root.join("user");
            return builder.like(builder.upper(userJoin.get("firstName")), "%"+firstName.toUpperCase()+"%");
        };
    }
    static Specification<Message> byUserLastNameLike(String lastName){
        return (root, query, builder) -> {
            Join<Message, User> userJoin = root.join("user");
            return builder.like(builder.upper(userJoin.get("lastName")), "%"+lastName.toUpperCase()+"%");
        };
    }
    static Specification<Message> byUserMiddleNameLike(String middleName){
        return (root, query, builder) -> {
            Join<Message, User> userJoin = root.join("user");
            return builder.like(builder.upper(userJoin.get("middleName")), "%"+middleName.toUpperCase()+"%");
        };
    }
    static Specification<Message> byUserPhoneNumberLike(String phoneNumber){
        return (root, query, builder) -> {
            Join<Message, User> userJoin = root.join("user");
            return builder.like(builder.upper(userJoin.get("phoneNumber")), "%"+phoneNumber.toUpperCase()+"%");
        };
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
