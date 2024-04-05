package com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.workerDetails.delegates.header;

public class WorkerDetailsHeaderModel {
    private final int id;
    private final String employeeId;
    private final String name;
    private final String role;

    public WorkerDetailsHeaderModel(int id, String employeeId, String name, String role) {
        this.id = id;
        this.employeeId = employeeId;
        this.name = name;
        this.role = role;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
