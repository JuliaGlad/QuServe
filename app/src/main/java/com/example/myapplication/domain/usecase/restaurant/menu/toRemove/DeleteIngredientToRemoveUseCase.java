package com.example.myapplication.domain.usecase.restaurant.menu.toRemove;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteIngredientToRemoveUseCase {
    public Completable invoke(String restaurantId, String categoryId, String dishId, String name){
        return RestaurantMenuDI.toRemove.deleteIngredientToRemove(restaurantId, categoryId, dishId, name);
    }
}
