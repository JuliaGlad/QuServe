package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteIngredientToRemoveUseCase {
    public Completable invoke(String restaurantId, String categoryId, String dishId, String name){
        return DI.restaurantOwnerRepository.deleteIngredientToRemove(restaurantId, categoryId, dishId, name);
    }
}