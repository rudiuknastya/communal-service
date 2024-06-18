package com.example.user.specification.specificationFormer;

import com.example.user.entity.Chairman;
import com.example.user.model.general.SelectSearchRequest;
import org.springframework.data.jpa.domain.Specification;

import static com.example.user.specification.ChairmanSpecification.*;

public class ChairmanSpecificationFormer {
    public static Specification<Chairman> formSelectSpecification(SelectSearchRequest selectSearchRequest) {
        Specification<Chairman> chairmanSpecification = Specification.where(byDeleted());
        if (!selectSearchRequest.search().isEmpty()){
            String[] fullName = selectSearchRequest.search().split(" ");
            if(fullName.length == 1 || fullName.length == 3) {
                chairmanSpecification = chairmanSpecification.and(byLastName(fullName[0]));
            }
            if (fullName.length == 2 || fullName.length == 3) {
                chairmanSpecification = chairmanSpecification.and(byFirstName(fullName[1]));
            }
            if (fullName.length == 3) {
                chairmanSpecification = chairmanSpecification.and(byMiddleName(fullName[2]));
            }
        }
        return chairmanSpecification;
    }
}
