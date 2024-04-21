package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateIngredientsToRemoveUseCase {
    public Completable invoke(String restaurantId, String categoryId, String dishId, String previousName, String newName){
        return DI.restaurantOwnerRepository.updateIngredientsToRemove(restaurantId, categoryId, dishId, previousName, newName);
    }
}
