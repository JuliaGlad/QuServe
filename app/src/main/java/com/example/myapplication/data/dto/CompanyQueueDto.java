package com.example.myapplication.data.dto;

import java.util.List;

public class CompanyQueueDto {
    private final String workersCount;
    private final String midTime;
    private final String passed;
    private final String passed15;
    private final String inProgress;
    private final List<Object> participants;
    private final String location;
    private final String city;
    private final String id;
    private final String name;
    private final String time;
    private final String companyId;

    public CompanyQueueDto(List<Object> participants, String id, String name, String time,
                           String companyId, String inProgress, String passed, String passed15,
                           String midTime, String location, String city, String workersCount) {

        this.participants = participants;
        this.id = id;
        this.name = name;
        this.time = time;
        this.inProgress = inProgress;
        this.companyId = companyId;
        this.passed = passed;
        this.passed15 = passed15;
        this.midTime = midTime;
        this.location = location;
        this.city = city;
        this.workersCount = workersCount;
    }

    public String getPassedLast15Minutes() {
        return passed15;
    }

    public String getCity() {
        return city;
    }

    public String getWorkersCount() {
        return workersCount;
    }

    public String getLocation() {
        return location;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getMidTime() {
        return midTime;
    }

    public int getPassed() {
        return Integer.parseInt(passed);
    }

    public String getInProgress() {
        return inProgress;
    }

    public List getParticipants() {
        return participants;
    }

    public String getTime() {
        return time;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
