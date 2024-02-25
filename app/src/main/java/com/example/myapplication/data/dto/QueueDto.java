package com.example.myapplication.data.dto;

import java.util.List;

public class QueueDto {
    private final String midTime;
    private final String passed;
    private final String inProgress;
    private final List<Object> participants;
    private final String id;
    private final String name;
    private final String time;
    private final String authorId;

    public QueueDto( List<Object> participants, String id, String name, String time, String authorId, String inProgress, String passed, String midTime) {
        this.participants = participants;
        this.id = id;
        this.name = name;
        this.time = time;
        this.inProgress = inProgress;
        this.authorId = authorId;
        this.passed = passed;
        this.midTime = midTime;
    }

    public String getMidTime(){return midTime;}

    public int getPassed(){return Integer.parseInt(passed);}

    public String getInProgress() {
        return inProgress;
    }

    public List getParticipants(){
        return participants;
    }

    public String getTime(){return time;}

    public String getId() {
        return id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getName() {
        return name;
    }
}
