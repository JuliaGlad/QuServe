package com.example.myapplication.domain.model.company;

public class CompanyQueueMidTimeModel {
    private final String midTime;
    private final int passed15;

    public CompanyQueueMidTimeModel(String midTime, int passed15) {
        this.midTime = midTime;
        this.passed15 = passed15;
    }

    public int getMidTime() {
        return Integer.parseInt(midTime);
    }

    public int getPassed15Minutes(){
        return passed15;
    }
}
