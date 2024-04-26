package com.example.myapplication.presentation.restaurantOrder.restaurantCart.orderCreated.state;

public interface OrderCreatedState {
    class Success implements OrderCreatedState{
        public String data;

        public Success(String data) {
            this.data = data;
        }
    }

    class Loading implements OrderCreatedState{

    }

    class Error implements OrderCreatedState{

    }
}
