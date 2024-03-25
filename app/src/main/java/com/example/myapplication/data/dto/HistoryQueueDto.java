package com.example.myapplication.data.dto;

public class HistoryQueueDto {

    private String date;
    private String name;
    private String time;

    public HistoryQueueDto(String date,String name, String time) {
        this.date = date;
        this.name = name;
        this.time = time;
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
