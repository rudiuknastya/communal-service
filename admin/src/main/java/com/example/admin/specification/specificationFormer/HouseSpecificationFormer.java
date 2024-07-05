package com.example.admin.specification.specificationFormer;

import com.example.admin.entity.House;
import com.example.admin.model.general.SelectSearchRequest;
import com.example.admin.model.houses.FilterRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static com.example.admin.specification.HouseSpecification.*;
@Component
public class HouseSpecificationFormer {
    public Specification<House> formTableSpecification(FilterRequest filterRequest){
        Specification<House> houseSpecification = Specification.where(byDeleted());
        if(!filterRequest.city().isEmpty()){
            houseSpecification = houseSpecification.and(byCityLike(filterRequest.city()));
        }
        if(!filterRequest.street().isEmpty()){
            houseSpecification = houseSpecification.and(byStreetLike(filterRequest.street()));
        }
        if(!filterRequest.number().isEmpty()){
            houseSpecification = houseSpecification.and(byNumberLike(filterRequest.number()));
        }
        if (filterRequest.chairmanId() != null){
            houseSpecification = houseSpecification.and(byChairmanId(filterRequest.chairmanId()));
        }
        if (filterRequest.status() != null){
            houseSpecification = houseSpecification.and(byStatus(filterRequest.status()));
        }
        return houseSpecification;
    }
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
