package com.example.myapplication.domain.usecase.restaurant.menu.toppings;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteToppingUseCase {
    public Completable invoke(String restaurantId, String categoryId, String dishId, String name){
        return RestaurantMenuDI.toppings.deleteTopping(restaurantId, categoryId, dishId, name);
    }
}
