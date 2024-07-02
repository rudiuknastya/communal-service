package com.example.user.specification.specificationFormer;

import com.example.user.entity.House;
import com.example.user.model.general.SelectSearchRequest;
import org.springframework.data.jpa.domain.Specification;

import static com.example.user.specification.HouseSpecification.*;

public class HouseSpecificationFormer {
    public static Specification<House> formCitySelectSpecification(SelectSearchRequest selectSearchRequest){
        Specification<House> houseSpecification = Specification.where(null);
        if(!selectSearchRequest.search().isEmpty()){
            houseSpecification = houseSpecification.and(byCityLike(selectSearchRequest.search()));
        }
        houseSpecification = houseSpecification.and(groupByCity());
        return houseSpecification;
    }
    public static Specification<House> formStreetSelectSpecification(SelectSearchRequest selectSearchRequest,
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
    public static Specification<House> formNumberSelectSpecification(SelectSearchRequest selectSearchRequest,
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
