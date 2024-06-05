package com.example.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "votes")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserVote userVote;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "voting_form_id", referencedColumnName = "id", nullable = false)
    private VotingForm votingForm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserVote getUserVote() {
        return userVote;
    }

    public void setUserVote(UserVote userVote) {
        this.userVote = userVote;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public VotingForm getVotingForm() {
        return votingForm;
    }

    public void setVotingForm(VotingForm votingForm) {
        this.votingForm = votingForm;
    }
}
