package com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.orderDetails.state;

public interface OrderDetailsWithIndicatorsState {
    class Success implements OrderDetailsWithIndicatorsState{
        public OrderDetailsWithIndicatorsStateModel data;

        public Success(OrderDetailsWithIndicatorsStateModel data) {
            this.data = data;
        }
    }

    class Loading implements OrderDetailsWithIndicatorsState{}

    class Error implements OrderDetailsWithIndicatorsState{}

    class OrderIsFinished implements OrderDetailsWithIndicatorsState{}
}
