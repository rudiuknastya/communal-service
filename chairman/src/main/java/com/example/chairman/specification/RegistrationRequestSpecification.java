package com.example.chairman.specification;

import com.example.chairman.entity.RegistrationRequest;
import com.example.chairman.entity.User;
import com.example.chairman.entity.enums.RequestStatus;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public interface RegistrationRequestSpecification {
    static Specification<RegistrationRequest> byDeleted(){
        return (root, query, builder) ->
                builder.equal(root.get("deleted"), false);
    }
    static Specification<RegistrationRequest> byUserEmail(String email){
        return (root, query, builder) -> {
            Join<RegistrationRequest, User> userJoin = root.join("user");
            return builder.like(builder.upper(userJoin.get("email")), "%"+email.toUpperCase()+"%");
        };
    }
    static Specification<RegistrationRequest> byUserFirstName(String firstName){
        return (root, query, builder) -> {
            Join<RegistrationRequest, User> userJoin = root.join("user");
            return builder.like(builder.upper(userJoin.get("firstName")), "%"+firstName.toUpperCase()+"%");
        };
    }
    static Specification<RegistrationRequest> byUserLastName(String lastName){
        return (root, query, builder) -> {
            Join<RegistrationRequest, User> userJoin = root.join("user");
            return builder.like(builder.upper(userJoin.get("lastName")), "%"+lastName.toUpperCase()+"%");
        };
    }
    static Specification<RegistrationRequest> byUserMiddleName(String middleName){
        return (root, query, builder) -> {
            Join<RegistrationRequest, User> userJoin = root.join("user");
            return builder.like(builder.upper(userJoin.get("middleName")), "%"+middleName.toUpperCase()+"%");
        };
    }
    static Specification<RegistrationRequest> byStatus(RequestStatus status){
        return (root, query, builder) ->
                builder.equal(root.get("status"), status);
    }
}
