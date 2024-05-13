package com.example.admin.specification.specificationFormer;

import com.example.admin.entity.House;
import com.example.admin.model.general.SelectSearchRequest;
import com.example.admin.model.houses.FilterRequest;
import org.springframework.data.jpa.domain.Specification;

import static com.example.admin.specification.HouseSpecification.*;

public class HouseSpecificationFormer {
    public static Specification<House> formSpecification(FilterRequest filterRequest){
        Specification<House> houseSpecification = Specification.where(byDeleted());
        if(!filterRequest.city().isEmpty()){
            houseSpecification = houseSpecification.and(byCity(filterRequest.city()));
        }
        if(!filterRequest.street().isEmpty()){
            houseSpecification = houseSpecification.and(byStreet(filterRequest.street()));
        }
        if(!filterRequest.number().isEmpty()){
            houseSpecification = houseSpecification.and(byNumber(filterRequest.number()));
        }
        if (filterRequest.chairmanId() != null){
            houseSpecification = houseSpecification.and(byChairmanId(filterRequest.chairmanId()));
        }
        if (filterRequest.status() != null){
            houseSpecification = houseSpecification.and(byStatus(filterRequest.status()));
        }
        return houseSpecification;
    }
    public static Specification<House> formCitySelectSpecification(SelectSearchRequest selectSearchRequest){
        Specification<House> houseSpecification = Specification.where(byDeleted());
        if(!selectSearchRequest.search().isEmpty()){
            houseSpecification = houseSpecification.and(byCity(selectSearchRequest.search()));
        }
        return houseSpecification;
    }
    public static Specification<House> formStreetSelectSpecification(SelectSearchRequest selectSearchRequest,
                                                                     String city, String number){
        Specification<House> houseSpecification = Specification.where(byDeleted().and(byCityEquals(city)));
        if(!selectSearchRequest.search().isEmpty()){
            houseSpecification = houseSpecification.and(byStreet(selectSearchRequest.search()));
        }
        if (!number.isEmpty()){
            houseSpecification = houseSpecification.and(byNumber(number));
        }
        return houseSpecification;
    }
    public static Specification<House> formNumberSelectSpecification(SelectSearchRequest selectSearchRequest,
                                                                     String city, String street){
        Specification<House> houseSpecification = Specification.where(byDeleted().and(byCityEquals(city)));
        if(!selectSearchRequest.search().isEmpty()){
            houseSpecification = houseSpecification.and(byNumber(selectSearchRequest.search()));
        }
        if(!street.isEmpty()){
            houseSpecification = houseSpecification.and(byStreetEquals(street));
        }
        return houseSpecification;
    }



}
