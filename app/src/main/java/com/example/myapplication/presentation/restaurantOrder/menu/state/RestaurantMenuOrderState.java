package com.example.myapplication.presentation.restaurantOrder.menu.state;

import com.example.myapplication.presentation.restaurantMenu.AddCategory.model.DishMenuModel;

import java.util.List;

public interface RestaurantMenuOrderState {
    class Success implements RestaurantMenuOrderState{
        public List<DishMenuModel> data;

        public Success(List<DishMenuModel> data) {
            this.data = data;
        }
    }
    
    class Loading implements RestaurantMenuOrderState{}
    
    class Error implements RestaurantMenuOrderState{}
}
