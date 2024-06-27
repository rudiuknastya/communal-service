package com.example.chairman.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Chairman chairman;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Chairman getChairman() {
        return chairman;
    }

    public void setChairman(Chairman chairman) {
        this.chairman = chairman;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
