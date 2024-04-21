package com.example.myapplication.presentation.restaurantOrder.dishDetails.state;

import com.example.myapplication.presentation.restaurantOrder.dishDetails.model.RestaurantOrderDishDetailsModel;

public interface RestaurantOrderDishDetailsState {
    class Success implements RestaurantOrderDishDetailsState{
        public RestaurantOrderDishDetailsModel data;

        public Success(RestaurantOrderDishDetailsModel data) {
            this.data = data;
        }
    }
    class Loading implements RestaurantOrderDishDetailsState{}

    class Error implements RestaurantOrderDishDetailsState{}
}
