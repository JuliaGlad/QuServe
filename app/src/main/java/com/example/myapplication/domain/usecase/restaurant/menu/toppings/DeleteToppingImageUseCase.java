package com.example.myapplication.domain.usecase.restaurant.menu.toppings;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteToppingImageUseCase {
    public Completable invoke(String restaurantId, String dishId, String name){
        return RestaurantMenuDI.toppings.deleteToppingImage(restaurantId, dishId, name);
    }
}
