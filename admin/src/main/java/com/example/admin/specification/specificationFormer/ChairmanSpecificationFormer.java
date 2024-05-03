package com.example.admin.specification.specificationFormer;

import com.example.admin.entity.Chairman;
import com.example.admin.model.chairmen.FilterRequest;
import org.springframework.data.jpa.domain.Specification;

import static com.example.admin.specification.ChairmanSpecification.*;

public class ChairmanSpecificationFormer {
    public static Specification<Chairman> formSpecification(FilterRequest filterRequest){
        Specification<Chairman> specification = Specification.where(byDeleted());
        if(!filterRequest.fullName().isEmpty()){
            String[] name = filterRequest.fullName().split(" ");
            if(name.length == 1 || name.length == 3) {
                specification = specification.and(byLastName(name[0]));
            }
            if (name.length == 2 || name.length == 3) {
                specification = specification.and(byFirstName(name[1]));
            }
            if (name.length == 3) {
                specification = specification.and(byMiddleName(name[2]));
            }
        }
        if(!filterRequest.phoneNumber().isEmpty()){
            specification = specification.and(byPhoneNumber(filterRequest.phoneNumber()));
        }
        if(filterRequest.status() != null){
            specification = specification.and(byStatus(filterRequest.status()));
        }
        return specification;
    }
}
