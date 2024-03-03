package com.example.myapplication.domain.model.queue;

import java.util.List;

public class QueueModel {

    private final String name;
    private final String midTime;
    private final List<String> participants;
    private final String id;

    public QueueModel(String name, List<String> participants, String id, String midTime) {
        this.name = name;
        this.participants = participants;
        this.id = id;
        this.midTime = midTime;
    }

    public String getMidTime(){return midTime;}

    public String getName() {
        return name;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public String getId() {
        return id;
    }
}
