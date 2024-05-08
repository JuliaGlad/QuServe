package com.example.myapplication.presentation.employee.main.restaurantCook.availableOrders.state;

import java.util.List;

public interface AvailableCookOrdersState {
    class Success implements AvailableCookOrdersState{
        public List<AvailableOrdersStateModel> data;

        public Success(List<AvailableOrdersStateModel> data) {
            this.data = data;
        }
    }
    class Loading implements AvailableCookOrdersState{}

    class Error implements AvailableCookOrdersState{}

}
