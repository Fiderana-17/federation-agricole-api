package com.hei.federation_api.Entity;

import java.util.List;

public class Member {

    public String id;
    public String firstName;
    public String lastName;
    public String email;

    public List<Member> referees;

    public Member() {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.referees = referees;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setReferees(List<Member> referees) {
        this.referees = referees;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<Member> getReferees() {
        return referees;
    }
}