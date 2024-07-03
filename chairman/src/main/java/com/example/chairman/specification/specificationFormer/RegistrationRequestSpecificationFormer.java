package com.example.chairman.specification.specificationFormer;

import com.example.chairman.entity.RegistrationRequest;
import com.example.chairman.model.registrationRequest.FilterRequest;
import org.springframework.data.jpa.domain.Specification;

import static com.example.chairman.specification.RegistrationRequestSpecification.*;

public class RegistrationRequestSpecificationFormer {
    public static Specification<RegistrationRequest> formTableSpecification(FilterRequest filterRequest){
        Specification<RegistrationRequest> registrationRequestSpecification = Specification.where(byDeleted());
        if (!filterRequest.email().isEmpty()){
            registrationRequestSpecification = registrationRequestSpecification
                    .and(byUserEmail(filterRequest.email()));
        }
        if (filterRequest.status() != null){
            registrationRequestSpecification = registrationRequestSpecification.and(byStatus(filterRequest.status()));
        }
        if (!filterRequest.fullName().isEmpty()){
            String[] fullName = filterRequest.fullName().split(" ");
            if(fullName.length == 1 || fullName.length == 3) {
                registrationRequestSpecification = registrationRequestSpecification.and(byUserLastName(fullName[0]));
            }
            if (fullName.length == 2 || fullName.length == 3) {
                registrationRequestSpecification = registrationRequestSpecification.and(byUserFirstName(fullName[1]));
            }
            if (fullName.length == 3) {
                registrationRequestSpecification = registrationRequestSpecification.and(byUserMiddleName(fullName[2]));
            }
        }
        return registrationRequestSpecification;
    }
}
