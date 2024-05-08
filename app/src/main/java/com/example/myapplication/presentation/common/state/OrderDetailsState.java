package com.example.myapplication.presentation.common.state;

public interface OrderDetailsState {
    class Success implements OrderDetailsState{
        public OrderDetailsStateModel data;

        public Success(OrderDetailsStateModel data) {
            this.data = data;
        }
    }
    class Loading implements OrderDetailsState{}

    class Error implements OrderDetailsState{}
}
