package com.freestack.evaluation.models;

import javax.persistence.*;

@Table(name = "uber_driver")
@Entity
public class UberDriver {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "available")
    private Boolean available;

    public UberDriver(String firstname, String lastname, Boolean available) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.available = available;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean availabe) {
        this.available = availabe;
    }
}
