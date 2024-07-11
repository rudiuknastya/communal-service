package com.example.user.specification.specificationFormer;

import com.example.user.entity.Message;
import com.example.user.model.messages.FilterRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static com.example.user.specification.MessageSpecification.*;
@Component
public class MessageSpecificationFormer {
    public Specification<Message> formTableSpecification(FilterRequest filterRequest){
        Specification<Message> messageSpecification = Specification.where(byDeleted());

        if(!filterRequest.subject().isEmpty()){
            messageSpecification = messageSpecification.and(bySubjectLike(filterRequest.subject()));
        }
        if(filterRequest.dateFrom() != null){
            messageSpecification = messageSpecification.and(byCreationDateGreaterThanOrEqual(filterRequest.dateFrom()));
        }
        if(filterRequest.dateTo() != null){
            messageSpecification = messageSpecification.and(byCreationDateLessThanOrEqual(filterRequest.dateTo()));
        }
        return messageSpecification;
    }
}
