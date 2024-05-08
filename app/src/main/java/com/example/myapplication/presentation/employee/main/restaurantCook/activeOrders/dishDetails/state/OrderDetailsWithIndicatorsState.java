package com.example.myapplication.presentation.employee.main.restaurantCook.activeOrders.dishDetails.state;

import java.util.List;

public interface OrderDetailsWithIndicatorsState {
    class Success implements OrderDetailsWithIndicatorsState{
        public OrderDetailsWithIndicatorsStateModel data;

        public Success(OrderDetailsWithIndicatorsStateModel data) {
            this.data = data;
        }
    }

    class Loading implements OrderDetailsWithIndicatorsState{}

    class Error implements OrderDetailsWithIndicatorsState{}
}
