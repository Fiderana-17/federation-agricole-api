package com.hei.federation_api.Entity;

import java.util.List;

public class CreateCollectivity {

    public String location;
    public List<String> members;
    public boolean federationApproval;
    public CreateCollectivityStructure structure;

    public CreateCollectivity() {}

    public CreateCollectivity(String location, List<String> members, boolean federationApproval, CreateCollectivityStructure structure) {
        this.location = location;
        this.members = members;
        this.federationApproval = federationApproval;
        this.structure = structure;
    }
}