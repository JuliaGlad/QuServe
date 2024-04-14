package com.example.myapplication.presentation.companyQueue.createQueue.chooseWorkers.recycler;

import com.example.myapplication.presentation.companyQueue.models.EmployeeModel;

import java.util.List;

public class WorkerItemModel {
    int id;
    String name;
    String workerId;
    String state;
    List<EmployeeModel> chosen;

    public WorkerItemModel(int id, String name, String workerId, String state, List<EmployeeModel> chosen ) {
        this.id = id;
        this.name = name;
        this.workerId = workerId;
        this.state = state;
        this.chosen = chosen;
    }

    public boolean compareToOther(WorkerItemModel other){
        return other.hashCode() == this.hashCode();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getWorkerId() {
        return workerId;
    }

    public String getState() {
        return state;
    }

    public List<EmployeeModel> getChosen() {
        return chosen;
    }
}
