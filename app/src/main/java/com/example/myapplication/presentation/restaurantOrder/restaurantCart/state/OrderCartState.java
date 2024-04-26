package com.example.myapplication.presentation.restaurantOrder.restaurantCart.state;

import com.example.myapplication.presentation.restaurantOrder.CartDishModel;

import java.util.List;

public interface OrderCartState {
    class Success implements OrderCartState{
       public OrderStateModel data;

        public Success(OrderStateModel data) {
            this.data = data;
        }
    }

    class Loading implements OrderCartState{}

    class Error implements OrderCartState{}
}
