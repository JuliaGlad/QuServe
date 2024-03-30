package com.example.myapplication.presentation.queue.waitingFragment.fragment.model;

import java.util.List;

public class WaitingModel {
    private final String name;
    private final String midTime;
    private final List<String> participants;
    private final String id;

    public WaitingModel(String name, List<String> participants, String id, String midTime) {
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
