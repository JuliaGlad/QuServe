package com.example.myapplication.domain.model;

import java.util.List;

public class QueueModel {

    private String name;
    private List<String> participants;
    private String id;

    public QueueModel(String name, List<String> participants, String id) {
        this.name = name;
        this.participants = participants;
        this.id = id;
    }

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
