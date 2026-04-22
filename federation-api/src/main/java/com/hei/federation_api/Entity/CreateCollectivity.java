package com.hei.federation_api.Entity;

import java.util.List;

public class CreateCollectivity {

    public String location;
    public List<String> members;
    public boolean federationApproval;
    public CreateCollectivityStructure structure;

    public CreateCollectivity(String location, List<String> members, boolean federationApproval, CreateCollectivityStructure structure) {
        this.location = location;
        this.members = members;
        this.federationApproval = federationApproval;
        this.structure = structure;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public boolean isFederationApproval() {
        return federationApproval;
    }

    public void setFederationApproval(boolean federationApproval) {
        this.federationApproval = federationApproval;
    }

    public CreateCollectivityStructure getStructure() {
        return structure;
    }

    public void setStructure(CreateCollectivityStructure structure) {
        this.structure = structure;
    }
}
