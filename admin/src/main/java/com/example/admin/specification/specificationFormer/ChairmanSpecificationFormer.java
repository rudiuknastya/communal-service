package com.example.admin.specification.specificationFormer;

import com.example.admin.entity.Chairman;
import com.example.admin.model.chairmen.FilterRequest;
import com.example.admin.model.general.SelectSearchRequest;
import org.springframework.data.jpa.domain.Specification;

import static com.example.admin.specification.ChairmanSpecification.*;

public class ChairmanSpecificationFormer {
    public static Specification<Chairman> formSpecification(FilterRequest filterRequest){
        Specification<Chairman> specification = Specification.where(byDeleted());
        if(!filterRequest.fullName().isEmpty()){
            String[] fullName = filterRequest.fullName().split(" ");
            specification = formFullNameSpecification(fullName, specification);
        }
        if(!filterRequest.phoneNumber().isEmpty()){
            specification = specification.and(byPhoneNumber(filterRequest.phoneNumber()));
        }
        if(filterRequest.status() != null){
            specification = specification.and(byStatus(filterRequest.status()));
        }
        return specification;
    }
    public static Specification<Chairman> formSelectSpecification(SelectSearchRequest selectSearchRequest){
        Specification<Chairman> specification = Specification.where(byDeleted());
        if(!selectSearchRequest.search().isEmpty()){
            String[] fullName = selectSearchRequest.search().split(" ");
            specification = formFullNameSpecification(fullName, specification);
        }
        return specification;
    }
    private static Specification<Chairman> formFullNameSpecification(String[] fullName, Specification<Chairman> specification){
        if(fullName.length == 1 || fullName.length == 3) {
            specification = specification.and(byLastName(fullName[0]));
        }
        if (fullName.length == 2 || fullName.length == 3) {
            specification = specification.and(byFirstName(fullName[1]));
        }
        if (fullName.length == 3) {
            specification = specification.and(byMiddleName(fullName[2]));
        }
        return specification;
    }
}
