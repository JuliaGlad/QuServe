package com.example.myapplication.presentation.companyQueue.createQueue.chooseWorkers.state;

import com.example.myapplication.presentation.companyQueue.createQueue.chooseWorkers.model.EmployeeStateModel;

import java.util.List;

public interface ChooseWorkersState {

    class Success implements ChooseWorkersState{
        public List<EmployeeStateModel> data;

        public Success(List<EmployeeStateModel> data) {
            this.data = data;
        }
    }

    class Loading implements ChooseWorkersState{}

    class Error implements ChooseWorkersState{}
}
