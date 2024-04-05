package com.example.myapplication.presentation.employee.main.queueWorkerFragment.model;

import com.example.myapplication.presentation.employee.main.ActiveQueueModel;

import java.util.List;

public class QueueWorkerStateModel {
    private final String companyName;
    private final String companyId;
    private final List<ActiveQueueModel> models;

    public QueueWorkerStateModel(String companyName, String companyId, List<ActiveQueueModel> models) {
        this.companyName = companyName;
        this.companyId = companyId;
        this.models = models;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public List<ActiveQueueModel> getModels() {
        return models;
    }
}
