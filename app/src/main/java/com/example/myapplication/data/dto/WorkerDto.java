package com.example.myapplication.data.dto;

public class WorkerDto {

    private final String name;
    private final String id;

    public WorkerDto(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
