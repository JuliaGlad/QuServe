package com.example.myapplication.presentation.employee.becomeWaiter.state;

public interface BecomeWaiterState {
    class Success implements BecomeWaiterState{
        public BecomeWaiterModel data;

        public Success(BecomeWaiterModel data) {
            this.data = data;
        }
    }

    class Loading implements BecomeWaiterState{}

    class Error implements BecomeWaiterState{}
}
