package com.example.admin.specification;

import com.example.admin.entity.Chairman;
import com.example.admin.entity.House;
import com.example.admin.entity.HouseStatus;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public interface HouseSpecification {
    static Specification<House> byDeleted(){
        return (root, query, builder) ->
                builder.equal(root.get("deleted"), false);
    }
    static Specification<House> byCity(String city){
        return (root, query, builder) ->
                builder.like(builder.upper(root.get("city")), "%"+city+"%");
    }
    static Specification<House> byStreet(String street){
        return (root, query, builder) ->
                builder.like(builder.upper(root.get("street")), "%"+street+"%");
    }
    static Specification<House> byNumber(Long number){
        return (root, query, builder) ->
                builder.equal(root.get("number"), number);
    }
    static Specification<House> byChairmanId(Long chairmanId){
        return (root, query, builder) -> {
            Join<House, Chairman> chairmanJoin = root.join("chairman");
           return builder.equal(chairmanJoin.get("id"), chairmanId);
        };
    }
    static Specification<House> byStatus(HouseStatus status){
        return (root, query, builder) ->
                builder.equal(root.get("status"), status);
    }


}
