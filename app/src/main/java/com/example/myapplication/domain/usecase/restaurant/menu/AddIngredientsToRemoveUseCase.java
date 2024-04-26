package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class AddIngredientsToRemoveUseCase {
    public Completable invoke(String restaurantId, String categoryId, String dishId, String variant){
        return DI.restaurantRepository.addIngredientToRemove(restaurantId, categoryId, dishId, variant);
    }
}
