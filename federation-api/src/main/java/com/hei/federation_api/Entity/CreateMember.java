package com.hei.federation_api.Entity;

import java.util.List;

public class CreateMember {

    public String firstName;
    public String lastName;
    public String birthDate;
    public String gender;
    public String address;
    public String profession;
    public Long phoneNumber;
    public String email;
    public String occupation;
    public String collectivityIdentifier;
    public List<String> referees;
    public boolean registrationFeePaid;
    public boolean membershipDuesPaid;

    public CreateMember() {}

    public CreateMember(String firstName, String lastName, String birthDate, String gender, String address, String profession, Long phoneNumber, String email, String occupation, String collectivityIdentifier, List<String> referees, boolean registrationFeePaid, boolean membershipDuesPaid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.profession = profession;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.occupation = occupation;
        this.collectivityIdentifier = collectivityIdentifier;
        this.referees = referees;
        this.registrationFeePaid = registrationFeePaid;
        this.membershipDuesPaid = membershipDuesPaid;
    }
}