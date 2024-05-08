package com.example.myapplication.domain.usecase.restaurant.menu.requireChoices;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateRequiredChoiceNameUseCase {
    public Completable invoke(String restaurantId, String categoryId, String dishId, String choiceId, String name){
        return RestaurantMenuDI.requiredChoice.updateRequiredChoiceName(restaurantId, categoryId, dishId, choiceId, name);
    }
}
