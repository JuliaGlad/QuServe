package com.example.myapplication.domain.model;

import java.util.List;

public class QueueParticipantsListModel {
    private final List<String> participants;
    private final String id;
    private final String inProgress;
    private final int passed;

    public QueueParticipantsListModel(List<String> participants, String id, String inProgress, int passed) {
        this.participants = participants;
        this.id = id;
        this.inProgress = inProgress;
        this.passed = passed;
    }

    public int getPassed(){return passed;}

    public String getInProgress() {
        return inProgress;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public String getId(){
        return id;
    }
}
