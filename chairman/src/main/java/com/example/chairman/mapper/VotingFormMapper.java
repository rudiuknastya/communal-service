package com.example.chairman.mapper;

import com.example.chairman.entity.VotingForm;
import com.example.chairman.model.voting.TableVotingFormResponse;
import com.example.chairman.model.voting.ViewVotingFormResponse;
import com.example.chairman.model.voting.VotingFormDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface VotingFormMapper {
    VotingForm votingFormDtoToVotingForm(VotingFormDto votingFormDto);
    VotingFormDto votingFormToVotingFormDto(VotingForm votingForm);
    void updateVotingForm(@MappingTarget VotingForm votingForm, VotingFormDto votingFormDto);
    @Mapping(target = "voted", source = "voted")
    TableVotingFormResponse votingFormToTableVotingFormResponse(VotingForm votingForm, String voted);
    @Mapping(target = "voted", source = "voted")
    @Mapping(target = "votesStatistic", source = "votesStatistic")
    ViewVotingFormResponse votingFormToViewVotingFormResponse(VotingForm votingForm, Long voted,
                                                              List<Long> votesStatistic);
}
