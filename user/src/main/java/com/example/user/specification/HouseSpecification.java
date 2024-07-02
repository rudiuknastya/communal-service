package com.example.user.specification;

import com.example.user.entity.House;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

public interface HouseSpecification {
    static Specification<House> byDeleted(){
        return (root, query, builder) ->
                builder.equal(root.get("deleted"), false);
    }
    static Specification<House> byCityLike(String city){
        return (root, query, builder) ->
                builder.like(builder.upper(root.get("city")), "%"+city.toUpperCase()+"%");
    }
    static Specification<House> byCityEquals(String city){
        return (root, query, builder) ->
                builder.equal(root.get("city"), city);
    }
    static Specification<House> groupByCity() {
        //select * from houses where id in (select max(id) from houses where id in (select id from houses where deleted = false) group by city);
        return (root, query, builder) ->{
            Subquery<Long> sub = query.subquery(Long.class);
            Subquery<Long> sub1 = sub.subquery(Long.class);
            Root<House> subRoot = sub.from(House.class);
            Root<House> subRoot1 = sub1.from(House.class);
            //select id from houses where deleted = false
            sub1.select(subRoot1.get("id")).where(builder.equal(subRoot1.get("deleted"), false));
            //select max(id) from houses where id in (select id from houses where deleted = false) group by city
            sub.select(builder.max(subRoot.get("id"))).where(subRoot.get("id").in(sub1)).groupBy(subRoot.get("city"));
            return root.get("id").in(sub);
        };
    }
    static Specification<House> groupByStreet() {
//      select * from houses where id in (select max(id) from houses where id in (select id from houses where deleted = false) group by street);
        return (root, query, builder) ->{
            Subquery<Long> sub = query.subquery(Long.class);
            Subquery<Long> sub1 = sub.subquery(Long.class);
            Root<House> subRoot = sub.from(House.class);
            Root<House> subRoot1 = sub1.from(House.class);
            //select id from houses where deleted = false
            sub1.select(subRoot1.get("id")).where(builder.equal(subRoot1.get("deleted"), false));
            //select max(id) from houses where id in (select id from houses where deleted = false) group by street
            sub.select(builder.max(subRoot.get("id"))).where(subRoot.get("id").in(sub1)).groupBy(subRoot.get("street"));
            return root.get("id").in(sub);
        };
    }

    static Specification<House> byStreetLike(String street){
        return (root, query, builder) ->
                builder.like(builder.upper(root.get("street")), "%"+street.toUpperCase()+"%");
    }
    static Specification<House> byStreetEquals(String street){
        return (root, query, builder) ->
                builder.equal(root.get("street"), street);
    }
    static Specification<House> byNumberLike(String number){
        return (root, query, builder) ->
                builder.like(builder.upper(root.get("number")), "%"+number.toUpperCase()+"%");
    }
    static Specification<House> byNumberEquals(String number){
        return (root, query, builder) ->
                builder.equal(root.get("number"), number);
    }
}
