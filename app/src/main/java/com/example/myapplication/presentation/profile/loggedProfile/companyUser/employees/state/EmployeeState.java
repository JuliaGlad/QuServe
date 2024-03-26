package com.example.myapplication.presentation.profile.loggedProfile.companyUser.employees.state;

import com.example.myapplication.presentation.profile.loggedProfile.companyUser.employees.model.EmployeeModel;

import java.util.List;

public interface EmployeeState {

    class Success implements EmployeeState{
        public List<EmployeeModel> data;

        public Success(List<EmployeeModel> data) {
            this.data = data;
        }
    }

    class Loading implements EmployeeState{}

    class Error implements EmployeeState{}
}
