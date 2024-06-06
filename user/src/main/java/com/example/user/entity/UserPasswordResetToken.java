package com.example.user.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
@Entity
public class UserPasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String token;
    @Column(nullable = false)
    private Instant expirationDate;
    private boolean used;
    private static final int EXPIRATION = 20;
    @OneToOne
    @JoinColumn(nullable = false)
    private User user;

    public UserPasswordResetToken() {
    }

    public UserPasswordResetToken(String token, User user) {
        this.token = token;
        this.user = user;
        setExpirationDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public void setExpirationDate() {
        this.expirationDate = calculateExpirationDate();
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }
    private Instant calculateExpirationDate(){
        return Instant.now().plus(EXPIRATION, ChronoUnit.MINUTES);
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
