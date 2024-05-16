package com.example.myapplication.presentation.employee.main.restaurantWaiter.startWork.state;

public interface StartWorkState {
    class Success implements StartWorkState{
        public String data;

        public Success(String data) {
            this.data = data;
        }
    }

    class Loading implements StartWorkState{}

    class Error implements StartWorkState{}
}
