package com.example.myapplication.presentation.employee.main.restaurantWaiter.main.state;

import java.util.List;

public class MainWaiterStateModel {
    private final String restaurantName;
    private final List<WaiterReadyDishesModel> dishes;

    public MainWaiterStateModel(String restaurantName, List<WaiterReadyDishesModel> dishes) {
        this.restaurantName = restaurantName;
        this.dishes = dishes;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public List<WaiterReadyDishesModel> getDishes() {
        return dishes;
    }
}
