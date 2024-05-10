package com.example.myapplication.presentation.employee.main.restaurantWaiter.main.state;

import java.util.List;

public interface MainWaiterState {
    class Success implements MainWaiterState{
            public MainWaiterStateModel data;

        public Success(MainWaiterStateModel data) {
            this.data = data;
        }
    }

    class Loading implements MainWaiterState{}

    class Error implements MainWaiterState{}
}
