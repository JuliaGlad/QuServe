package com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.main.state;

import androidx.lifecycle.LiveData;

import com.example.myapplication.presentation.employee.main.queueAdminFragment.workerManager.model.CompanyEmployeeModel;

import java.util.List;

public interface WorkerManagerState {

    class Success implements WorkerManagerState{
        public List<CompanyEmployeeModel> data;

        public Success(List<CompanyEmployeeModel> data) {
            this.data = data;
        }
    }

    class Loading implements WorkerManagerState{}

    class Error implements WorkerManagerState{}
}
