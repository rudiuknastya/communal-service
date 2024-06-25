package com.example.chairman.repository;

import com.example.chairman.entity.UserVote;
import com.example.chairman.entity.Vote;
import com.example.chairman.entity.VotingForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long>, JpaSpecificationExecutor<Vote> {
    @Query(value = "SELECT count(id) FROM votes where voting_form_id = :id", nativeQuery = true)
    Long getVotesCountByVotingFormId(@Param("id") Long id);
    @Query(value = "SELECT count(id) FROM votes where voting_form_id = :id and user_vote='AGREE'", nativeQuery = true)
    Long getAgreeVoteCountByVotingFormId(@Param("id") Long id);
    @Query(value = "SELECT count(id) FROM votes where voting_form_id = :id and user_vote='DISAGREE'", nativeQuery = true)
    Long getDisagreeVoteCountByVotingFormId(@Param("id") Long id);
    @Query(value = "SELECT count(id) FROM votes where voting_form_id = :id and user_vote='ABSTAIN'", nativeQuery = true)
    Long getAbstainVoteCountByVotingFormId(@Param("id") Long id);
    Long countByVotingFormIdAndUserVote(Long id, UserVote userVote);
    @Query(value = "SELECT sum(area) FROM votes join users u on u.id = votes.user_id where voting_form_id = :id", nativeQuery = true)
    Long calculateQuorum(@Param("id")Long votingFormId);
}
