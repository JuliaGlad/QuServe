package com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.workerDetails.model;

import static com.example.myapplication.presentation.utils.constants.Utils.WORKER;

import com.example.myapplication.presentation.employee.main.ActiveQueueModel;

import java.util.List;

public class WorkerDetailsModel {
    private final String employeeName;
    private final String employeeId;
    private final List<ActiveQueueModel> models;

    public WorkerDetailsModel(String employeeName, String employeeId, List<ActiveQueueModel> models) {
        this.employeeName = employeeName;
        this.employeeId = employeeId;
        this.models = models;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public List<ActiveQueueModel> getModels() {
        return models;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmployeeRole() {
        return WORKER;
    }
}
