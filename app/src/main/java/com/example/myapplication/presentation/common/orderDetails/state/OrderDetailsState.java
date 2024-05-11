package com.example.myapplication.presentation.common.orderDetails.state;

public interface OrderDetailsState {
    class Success implements OrderDetailsState{
        public OrderDetailsStateModel data;

        public Success(OrderDetailsStateModel data) {
            this.data = data;
        }
    }
    class Loading implements OrderDetailsState{}

    class Error implements OrderDetailsState{}

    class OrderIsFinished implements OrderDetailsState{}
}
