package com.example.chairman.mapper;

import com.example.chairman.entity.VotingForm;
import com.example.chairman.model.voting.VotingFormDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface VotingFormMapper {
    VotingForm votingFormDtoToVotingForm(VotingFormDto votingFormDto);
}
