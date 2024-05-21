package com.example.chairman.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "chairman_password_reset_tokens")
public class ChairmanPasswordResetToken {
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
    @JoinColumn(nullable = false, name = "chairman_id", referencedColumnName = "id")
    private Chairman chairman;

    public ChairmanPasswordResetToken() {
    }

    public ChairmanPasswordResetToken(String token, Chairman chairman) {
        this.token = token;
        this.chairman = chairman;
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

    public Chairman getChairman() {
        return chairman;
    }

    public void setChairman(Chairman chairman) {
        this.chairman = chairman;
    }
}
