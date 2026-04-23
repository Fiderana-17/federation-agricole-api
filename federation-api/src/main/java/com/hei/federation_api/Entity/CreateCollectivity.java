package com.hei.federation_api.Entity;

import java.util.List;

public class CreateCollectivity {

    public String location;
    public String specialization;
    public List<String> members;
    public boolean federationApproval;
    public CreateCollectivityStructure structure;

    public CreateCollectivity() {}
}