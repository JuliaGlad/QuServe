package com.example.myapplication.data.dto.user;

public class HistoryQueueDto {

    private final String date;
    private final String service;
    private final String name;
    private final String time;

    public HistoryQueueDto(String date, String service, String name, String time) {
        this.date = date;
        this.service = service;
        this.name = name;
        this.time = time;
    }

    public String getService() {
        return service;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }


}
