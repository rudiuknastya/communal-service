package com.example.chairman.repository;

import com.example.chairman.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    @Query(value = "SELECT count(id) FROM votes where voting_form_id = :id", nativeQuery = true)
    Long getVotesCountByVotingFormId(@Param("id") Long id);
    @Query(value = "SELECT count(id) FROM votes where voting_form_id = :id and user_vote='AGREE'", nativeQuery = true)
    Long getAgreeVoteCountByVotingFormId(@Param("id") Long id);
    @Query(value = "SELECT count(id) FROM votes where voting_form_id = :id and user_vote='DISAGREE'", nativeQuery = true)
    Long getDisagreeVoteCountByVotingFormId(@Param("id") Long id);
    @Query(value = "SELECT count(id) FROM votes where voting_form_id = :id and user_vote='ABSTAIN'", nativeQuery = true)
    Long getAbstainVoteCountByVotingFormId(@Param("id") Long id);
}
