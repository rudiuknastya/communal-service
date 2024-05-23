package com.example.chairman.specification.specificationFormer;

import com.example.chairman.entity.VotingForm;
import com.example.chairman.model.voting.FilterRequest;
import org.springframework.data.jpa.domain.Specification;

import static com.example.chairman.specification.VotingFormSpecification.*;

public class VotingFormSpecificationFormer {
    public static Specification<VotingForm> formSpecification(FilterRequest filterRequest){
        Specification<VotingForm> votingFormSpecification = Specification.where(byDeleted());
        if (!filterRequest.subject().isEmpty()) {
            votingFormSpecification = votingFormSpecification.and(bySubjectLike(filterRequest.subject()));
        }
        if (filterRequest.endDate() != null) {
            votingFormSpecification = votingFormSpecification.and(byEndDate(filterRequest.endDate()));
        }
        if (filterRequest.status() != null) {
            votingFormSpecification = votingFormSpecification.and(byStatus(filterRequest.status()));
        }
        if (filterRequest.resultStatus() != null) {
            votingFormSpecification = votingFormSpecification.and(byResultStatus(filterRequest.resultStatus()));
        }
        return votingFormSpecification;
    }
}
