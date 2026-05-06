package com.hei.federation_api.Entity;

public class CollectivityOverallStatistics {
    public CollectivityInformation collectivityInformation;
    public Integer newMembersNumber;
    public Double overallMemberCurrentDuePercentage;
    public Double overallMemberAssiduityPercentage;
    public transient String collectivityId;

    public CollectivityOverallStatistics() {}
}