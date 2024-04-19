package com.example.myapplication.presentation.restaurantMenu.AddCategory.state;

import com.example.myapplication.presentation.restaurantMenu.AddCategory.model.DishMenuModel;

import java.util.List;

public interface RestaurantMenuState {
    class Success implements RestaurantMenuState{
       public List<DishMenuModel> data;

        public Success(List<DishMenuModel> data) {
            this.data = data;
        }
    }

    class Loading implements RestaurantMenuState{}

    class Error implements RestaurantMenuState{}
}
