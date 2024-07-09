package com.example.chairman.specification.specificationFormer;


import com.example.chairman.entity.House;
import com.example.chairman.model.general.SelectSearchRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static com.example.chairman.specification.HouseSpecification.*;
@Component
public class HouseSpecificationFormer {
    public Specification<House> formCitySelectSpecification(SelectSearchRequest selectSearchRequest){
        Specification<House> houseSpecification = Specification.where(null);
        if(!selectSearchRequest.search().isEmpty()){
            houseSpecification = houseSpecification.and(byCityLike(selectSearchRequest.search()));
        }
        houseSpecification = houseSpecification.and(groupByCity());
        return houseSpecification;
    }
    public Specification<House> formStreetSelectSpecification(SelectSearchRequest selectSearchRequest,
                                                                     String city, String number){
        Specification<House> houseSpecification = Specification.where(byCityEquals(city));
        if(!selectSearchRequest.search().isEmpty()){
            houseSpecification = houseSpecification.and(byStreetLike(selectSearchRequest.search()));
        }
        if (!number.isEmpty()){
            houseSpecification = houseSpecification.and(byNumberEquals(number)).and(byDeleted());
        } else {
            houseSpecification = houseSpecification.and(groupByStreet());
        }
        return houseSpecification;
    }
    public Specification<House> formNumberSelectSpecification(SelectSearchRequest selectSearchRequest,
                                                                     String city, String street){
        Specification<House> houseSpecification = Specification.where(byDeleted().and(byCityEquals(city)));
        if(!selectSearchRequest.search().isEmpty()){
            houseSpecification = houseSpecification.and(byNumberLike(selectSearchRequest.search()));
        }
        if(!street.isEmpty()){
            houseSpecification = houseSpecification.and(byStreetEquals(street));
        }
        return houseSpecification;
    }



}
