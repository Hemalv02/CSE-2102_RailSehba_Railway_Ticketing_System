package com.oopporject.railsheba.model;

import java.time.LocalDate;

public class User implements Entity {
    private String name;
    private String email;
    private String number;
    private String nid;
    private LocalDate birthDate;


    public User(String name, String email, String number, String nid, LocalDate birthdate) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.nid = nid;
        this.birthDate = birthdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
