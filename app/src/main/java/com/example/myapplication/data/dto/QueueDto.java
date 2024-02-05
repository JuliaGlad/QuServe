package com.example.myapplication.data.dto;

public class QueueDto {
    private String id;
    private String name;
    private String time;
    private String authorId;

    public QueueDto(String id, String name, String time, String authorId) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.authorId = authorId;
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
