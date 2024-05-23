package com.example.chairman.specification;

import com.example.chairman.entity.VotingForm;
import com.example.chairman.entity.VotingResultStatus;
import com.example.chairman.entity.VotingStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public interface VotingFormSpecification {
    static Specification<VotingForm> byDeleted(){
        return (root, query, builder) ->
                builder.equal(root.get("deleted"), false);
    }
    static Specification<VotingForm> bySubjectLike(String subject){
        return (root, query, builder) ->
                builder.like(builder.upper(root.get("subject")), "%"+subject.toUpperCase()+"%");
    }
    static Specification<VotingForm> byEndDate(LocalDate date){
        return (root, query, builder) ->
                builder.equal(builder.function("date", LocalDate.class,root.get("endDate")), date);
    }
    static Specification<VotingForm> byStatus(VotingStatus status){
        return (root, query, builder) ->
                builder.equal(root.get("status"), status);
    }
    static Specification<VotingForm> byResultStatus(VotingResultStatus resultStatus){
        return (root, query, builder) ->
                builder.equal(root.get("resultStatus"), resultStatus);
    }

}
