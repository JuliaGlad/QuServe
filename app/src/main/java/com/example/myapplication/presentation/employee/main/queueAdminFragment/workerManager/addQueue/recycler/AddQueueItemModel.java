package com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.addQueue.recycler;

import com.example.myapplication.presentation.employee.main.ActiveQueueModel;

import java.util.List;

import myapplication.android.ui.listeners.ButtonItemListener;
import myapplication.android.ui.listeners.ButtonObjectListener;

public class AddQueueItemModel {
    int id;
    String queueId;
    String queueName;
    String workers;
    String location;
    String city;
    String state;
    List<ActiveQueueModel> chosen;
    ButtonObjectListener addListener;
    ButtonObjectListener removeListener;

    public AddQueueItemModel(int id, String queueId, String queueName, String workers, String location, String city, String state, List<ActiveQueueModel> chosen, ButtonObjectListener addListener, ButtonObjectListener removeListener) {
        this.id = id;
        this.queueId = queueId;
        this.queueName = queueName;
        this.workers = workers;
        this.location = location;
        this.city = city;
        this.state = state;
        this.chosen = chosen;
        this.addListener = addListener;
        this.removeListener = removeListener;
    }

    public String getLocation() {
        return location;
    }

    public String getCity() {
        return city;
    }

    public boolean compareToOther(AddQueueItemModel other){
        return other.id == id;
    }
}
