package com.hei.federation_api.Entity;

import java.util.List;

public class CreateCollectivityActivity {
    public String label;
    public String activityType; // MEETING, TRAINING, OTHER
    public List<String> memberOccupationConcerned;
    public MonthlyRecurrenceRule recurrenceRule;
    public String executiveDate;

    public CreateCollectivityActivity() {}
}