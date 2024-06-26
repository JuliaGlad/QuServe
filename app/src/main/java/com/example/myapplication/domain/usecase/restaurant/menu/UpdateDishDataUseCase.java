package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateDishDataUseCase {
    public Completable invoke(String restaurantId, String categoryId, String dishId, String name, String ingredients, String price, String weightOrCount){
        return RestaurantMenuDI.restaurantMenuRepository.updateDishData(restaurantId, categoryId, dishId, name, ingredients, price, weightOrCount);
    }
}
