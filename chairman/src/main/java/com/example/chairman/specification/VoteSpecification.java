package com.example.chairman.specification;

import com.example.chairman.entity.*;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public interface VoteSpecification {
    static Specification<Vote> byUserFirstName(String firstName){
        return (root, query, builder) -> {
            Join<Vote, User> userJoin = root.join("user");
            return builder.like(builder.upper(userJoin.get("firstName")),
                    "%"+firstName.toUpperCase()+"%");
        };
    }
    static Specification<Vote> byUserMiddleName(String middleName){
        return (root, query, builder) -> {
            Join<Vote, User> userJoin = root.join("user");
            return builder.like(builder.upper(userJoin.get("middleName")),
                    "%"+middleName.toUpperCase()+"%");
        };
    }
    static Specification<Vote> byUserLastName(String lastName){
        return (root, query, builder) -> {
            Join<Vote, User> userJoin = root.join("user");
            return builder.like(builder.upper(userJoin.get("lastName")),
                    "%"+lastName.toUpperCase()+"%");
        };
    }
    static Specification<Vote> byUserApartmentNumber(Long apartmentNumber){
        return (root, query, builder) -> {
            Join<Vote, User> userJoin = root.join("user");
            return builder.equal(userJoin.get("apartmentNumber"), apartmentNumber);
        };
    }
    static Specification<Vote> byUserArea(BigDecimal area){
        return (root, query, builder) -> {
            Join<Vote, User> userJoin = root.join("user");
            return builder.equal(userJoin.get("area"), area);
        };
    }
    static Specification<Vote> byUserPhoneNumber(String phoneNumber){
        return (root, query, builder) -> {
            Join<Vote, User> userJoin = root.join("user");
            return builder.like(builder.upper(userJoin.get("phoneNumber")),
                    "%"+phoneNumber.toUpperCase()+"%");
        };
    }
    static Specification<Vote> byUserVote(UserVote userVote){
        return (root, query, builder) ->
                builder.equal(root.get("userVote"), userVote);
    }
    static Specification<Vote> byVotingFormId(Long id){
        VotingForm votingForm = new VotingForm();
        votingForm.setId(id);
        return (root, query, builder) ->
                builder.equal(root.get("votingForm"), votingForm);
    }

}
