package com.example.myapplication.domain.usecase.restaurant.menu.toRemove;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateIngredientsToRemoveUseCase {
    public Completable invoke(String restaurantId, String categoryId, String dishId, String previousName, String newName){
        return RestaurantMenuDI.toRemove.updateIngredientsToRemove(restaurantId, categoryId, dishId, previousName, newName);
    }
}
