package com.example.myapplication.domain.usecase.restaurant.menu.toRemove;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;

import io.reactivex.rxjava3.core.Completable;

public class AddIngredientsToRemoveUseCase {
    public Completable invoke(String restaurantId, String categoryId, String dishId, String variant){
        return RestaurantMenuDI.toRemove.addIngredientToRemove(restaurantId, categoryId, dishId, variant);
    }
}
