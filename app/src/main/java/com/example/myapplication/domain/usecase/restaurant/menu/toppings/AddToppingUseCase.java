package com.example.myapplication.domain.usecase.restaurant.menu.toppings;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;

import io.reactivex.rxjava3.core.Completable;

public class AddToppingUseCase {
    public Completable invoke(String restaurantId, String categoryId, String dishId, String name, String price){
        return RestaurantMenuDI.toppings.addTopping(restaurantId, categoryId, dishId, name, price);
    }
}
