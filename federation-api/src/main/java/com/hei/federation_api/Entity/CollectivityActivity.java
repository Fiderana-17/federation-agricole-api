package com.hei.federation_api.Entity;

import java.util.List;

public class CollectivityActivity {
    public String id;
    public String label;
    public String activityType;
    public List<String> memberOccupationConcerned;
    public MonthlyRecurrenceRule recurrenceRule;
    public String executiveDate;
    public String collectivityId;

    public CollectivityActivity() {}
}