package com.example.myapplication.domain.usecase.restaurant.menu.requireChoices;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteRequiredChoiceByIdUseCase {
    public Completable invoke(String restaurantId, String categoryId, String dishId, String choiceId){
        return RestaurantMenuDI.requiredChoice.deleteRequiredChoiceById(restaurantId, categoryId, dishId, choiceId);
    }
}
