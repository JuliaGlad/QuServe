package com.example.myapplication.data.dto;

import java.util.ArrayList;
import java.util.List;

public class QueueDto {
    private List<String> participants;
    private String id;
    private String name;
    private String time;
    private String authorId;

    public QueueDto( List<String> participants, String id, String name, String time, String authorId) {
        this.participants = participants;
        this.id = id;
        this.name = name;
        this.time = time;
        this.authorId = authorId;
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
