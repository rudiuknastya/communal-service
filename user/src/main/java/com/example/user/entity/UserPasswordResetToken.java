package com.example.user.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
@Entity
public class UserPasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime expirationDate;
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

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }
    private LocalDateTime calculateExpirationDate(){
        return LocalDateTime.now().plusMinutes(EXPIRATION);
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
