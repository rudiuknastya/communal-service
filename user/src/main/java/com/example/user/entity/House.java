package com.example.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "houses")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String city;
    @Column(length = 200, nullable = false)
    private String street;
    @Column(length = 10, nullable = false)
    private String number;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HouseStatus status;
    @ManyToOne
    @JoinColumn(name = "chairman_id", referencedColumnName = "id", nullable = false)
    private Chairman chairman;
    private boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public HouseStatus getStatus() {
        return status;
    }

    public void setStatus(HouseStatus status) {
        this.status = status;
    }

    public Chairman getChairman() {
        return chairman;
    }

    public void setChairman(Chairman chairman) {
        this.chairman = chairman;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
