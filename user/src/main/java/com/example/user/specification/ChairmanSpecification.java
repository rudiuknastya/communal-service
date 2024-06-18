package com.example.user.specification;

import com.example.user.entity.Chairman;
import org.springframework.data.jpa.domain.Specification;

public interface ChairmanSpecification {
    static Specification<Chairman> byDeleted(){
        return (root, query, builder) ->
                builder.equal(root.get("deleted"), false);
    }
    static Specification<Chairman> byFirstName(String firstName){
        return (root, query, builder) ->
                builder.like(builder.upper(root.get("firstName")), "%"+firstName+"%");
    }
    static Specification<Chairman> byLastName(String lastName){
        return (root, query, builder) ->
                builder.like(builder.upper(root.get("lastName")), "%"+lastName+"%");
    }

    static Specification<Chairman> byMiddleName(String middleName){
        return (root, query, builder) ->
                builder.like(builder.upper(root.get("middleName")), "%"+middleName+"%");
    }
}
