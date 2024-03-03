package com.example.myapplication.domain.model.queue;

public class QueueMidTimeModel {
    private final String id;
    private final String midTime;
    private final int passed;

    public QueueMidTimeModel(String midTime, String id, int passed) {
        this.midTime = midTime;
        this.id = id;
        this.passed = passed;
    }

    public String getId(){return id;}

    public int getMidTime() {
        return Integer.parseInt(midTime);
    }

    public int getPassed(){
        return passed;
    }
}
