package com.example.myapplication.domain.model;

import java.util.List;

public class QueueParticipantsListModel {
    private List<String> participants;
    private String id;

    public QueueParticipantsListModel(List<String> participants, String id) {
        this.participants = participants;
        this.id = id;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public String getId(){
        return id;
    }
}
