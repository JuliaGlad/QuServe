package com.example.myapplication.presentation.home.restaurantUser.state;

import java.util.List;

public interface RestaurantHomeState {
    class Success implements RestaurantHomeState{
        public List<RestaurantHomeLocationModel> data;

        public Success(List<RestaurantHomeLocationModel> data) {
            this.data = data;
        }
    }
    class Loading implements RestaurantHomeState{}

    class Error implements RestaurantHomeState{}
}
