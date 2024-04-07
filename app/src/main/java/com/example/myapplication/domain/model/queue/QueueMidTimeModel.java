package com.example.myapplication.domain.model.queue;

public class QueueMidTimeModel {
    private final String id;
    private final String midTime;
    private final int passed15;

    public QueueMidTimeModel(String midTime, String id, int passed15) {
        this.midTime = midTime;
        this.id = id;
        this.passed15 = passed15;
    }

    public String getId(){return id;}

    public int getMidTime() {
        return Integer.parseInt(midTime);
    }

    public int getPassed15Minutes(){
        return passed15;
    }
}
