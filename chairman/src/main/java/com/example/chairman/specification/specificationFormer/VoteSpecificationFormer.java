package com.example.chairman.specification.specificationFormer;

import com.example.chairman.entity.Vote;
import com.example.chairman.model.voting.UsersFilterRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static com.example.chairman.specification.VoteSpecification.*;
@Component
public class VoteSpecificationFormer {
    public Specification<Vote> formTableSpecification(Long id, UsersFilterRequest usersFilterRequest){
        Specification<Vote> voteSpecification = Specification.where(byVotingFormId(id));
        if(!usersFilterRequest.fullName().isEmpty()){
            String[] fullName = usersFilterRequest.fullName().split(" ");
            if(fullName.length == 1 || fullName.length == 3) {
                voteSpecification = voteSpecification.and(byUserLastName(fullName[0]));
            }
            if (fullName.length == 2 || fullName.length == 3) {
                voteSpecification = voteSpecification.and(byUserFirstName(fullName[1]));
            }
            if (fullName.length == 3) {
                voteSpecification = voteSpecification.and(byUserMiddleName(fullName[2]));
            }
        }
        if(usersFilterRequest.apartmentNumber() != null){
            voteSpecification = voteSpecification.and(byUserApartmentNumber(usersFilterRequest.apartmentNumber()));
        }
        if(usersFilterRequest.area() != null){
            voteSpecification = voteSpecification.and(byUserArea(usersFilterRequest.area()));
        }
        if(!usersFilterRequest.phoneNumber().isEmpty()){
            voteSpecification = voteSpecification.and(byUserPhoneNumber(usersFilterRequest.phoneNumber()));
        }
        if(usersFilterRequest.userVote() != null){
            voteSpecification = voteSpecification.and(byUserVote(usersFilterRequest.userVote()));
        }
        return voteSpecification;
    }
}
