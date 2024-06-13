package com.example.user.repository;

import com.example.user.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long>, JpaSpecificationExecutor<Vote> {
    @Query(value = "SELECT count(id) FROM votes where voting_form_id = :id", nativeQuery = true)
    Long getVotesCountByVotingFormId(@Param("id") Long id);
    @Query(value = "SELECT count(id) FROM votes where voting_form_id = :id and user_vote='AGREE'", nativeQuery = true)
    Long getAgreeVoteCountByVotingFormId(@Param("id") Long id);
    @Query(value = "SELECT count(id) FROM votes where voting_form_id = :id and user_vote='DISAGREE'", nativeQuery = true)
    Long getDisagreeVoteCountByVotingFormId(@Param("id") Long id);
    @Query(value = "SELECT count(id) FROM votes where voting_form_id = :id and user_vote='ABSTAIN'", nativeQuery = true)
    Long getAbstainVoteCountByVotingFormId(@Param("id") Long id);
    Optional<Vote> findByVotingFormIdAndUserUsername(Long id, String username);
}
