package com.example.chairman.specification.specificationFormer;

import com.example.chairman.entity.Message;
import com.example.chairman.model.message.FilterRequest;
import org.springframework.data.jpa.domain.Specification;

import static com.example.chairman.specification.MessageSpecification.*;
public class MessageSpecificationFormer {
    public static Specification<Message> formSpecification(FilterRequest filterRequest){
        Specification<Message> messageSpecification = Specification.where(byDeleted());
        if(!filterRequest.fullName().isEmpty()){
            String[] fullName = filterRequest.fullName().split(" ");
            if(fullName.length == 1 || fullName.length == 3) {
                messageSpecification = messageSpecification.and(byUserLastNameLike(fullName[0]));
            }
            if (fullName.length == 2 || fullName.length == 3) {
                messageSpecification = messageSpecification.and(byUserFirstNameLike(fullName[1]));
            }
            if (fullName.length == 3) {
                messageSpecification = messageSpecification.and(byUserMiddleNameLike(fullName[2]));
            }
        }
        if(filterRequest.apartmentNumber() != null){
            messageSpecification = messageSpecification.and(byUserApartmentNumber(filterRequest.apartmentNumber()));
        }
        if(!filterRequest.phoneNumber().isEmpty()){
            messageSpecification = messageSpecification.and(byUserPhoneNumberLike(filterRequest.phoneNumber()));
        }
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
