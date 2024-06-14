package com.example.user.mapper;

import com.example.user.entity.User;
import com.example.user.entity.UserVote;
import com.example.user.entity.Vote;
import com.example.user.entity.VotingForm;
import com.example.user.model.voting.ActiveVotingResponse;
import com.example.user.model.voting.ClosedVotingResponse;
import com.example.user.model.voting.TableVotingFormResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface VotingFormMapper {
    @Mapping(target = "voted", source = "voted")
    TableVotingFormResponse votingFormToTableVotingFormResponse(VotingForm votingForm, String voted);
    @Mapping(target = "totalVotes", source = "totalVotes")
    @Mapping(target = "vote", source = "vote")
    @Mapping(target = "subject", source = "votingForm.subject")
    @Mapping(target = "text", source = "votingForm.text")
    @Mapping(target = "endDate", source = "votingForm.endDate")
    ActiveVotingResponse votingToActiveVotingResponse(VotingForm votingForm, Long totalVotes, UserVote vote);
    @Mapping(target = "votingForm", source = "votingForm")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "userVote", source = "userVote")
    @Mapping(target = "id", ignore = true)
    Vote createVote(VotingForm votingForm, User user, UserVote userVote);
    @Mapping(target = "vote", source = "userVote")
    @Mapping(target = "votesStatistic", source = "votesStatistic")
    ClosedVotingResponse createClosedVotingResponse(VotingForm votingForm,
                                                    UserVote userVote, List<Long> votesStatistic);
}
