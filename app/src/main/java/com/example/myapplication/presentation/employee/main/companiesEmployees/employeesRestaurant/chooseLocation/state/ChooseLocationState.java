package com.example.myapplication.presentation.employee.main.companiesEmployees.employeesRestaurant.chooseLocation.state;

import java.util.List;

public interface ChooseLocationState {
    class Success implements ChooseLocationState{
        public List<ChooseLocationStateModel> data;

        public Success(List<ChooseLocationStateModel> data) {
            this.data = data;
        }
    }

    class Loading implements ChooseLocationState{}

    class Error implements ChooseLocationState{}
}
