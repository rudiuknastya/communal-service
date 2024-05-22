package com.example.chairman.mapper;

import com.example.chairman.entity.VotingForm;
import com.example.chairman.model.voting.VotingFormDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface VotingFormMapper {
    VotingForm votingFormDtoToVotingForm(VotingFormDto votingFormDto);
    VotingFormDto votingFormToVotingFormDto(VotingForm votingForm);
    void updateVotingForm(@MappingTarget VotingForm votingForm, VotingFormDto votingFormDto);
}
