package com.hei.federation_api.Entity;

import java.util.List;

public class Collectivity {

    public String id;
    public String name;
    public String number;
    public String location;
    public boolean federationApproval;
    public List<Member> members;

    public Collectivity() {}
}