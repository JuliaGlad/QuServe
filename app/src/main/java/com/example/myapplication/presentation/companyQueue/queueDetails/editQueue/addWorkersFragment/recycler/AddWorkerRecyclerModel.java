package com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.addWorkersFragment.recycler;

import com.example.myapplication.presentation.companyQueue.queueDetails.editQueue.addWorkersFragment.model.AddWorkerModel;

import java.util.List;

public class AddWorkerRecyclerModel {
    private final int id;
    private final String name;
    private final String userId;
    String state;
    List<AddWorkerModel> chosen;

    public AddWorkerRecyclerModel(int id, String name, String userId, String state, List<AddWorkerModel> chosen ) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.state = state;
        this.chosen = chosen;
    }

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public boolean compareTo(AddWorkerRecyclerModel other){
        return other.hashCode() == this.hashCode();
    }
}
