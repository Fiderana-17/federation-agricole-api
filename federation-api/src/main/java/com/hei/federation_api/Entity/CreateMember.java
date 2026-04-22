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
}