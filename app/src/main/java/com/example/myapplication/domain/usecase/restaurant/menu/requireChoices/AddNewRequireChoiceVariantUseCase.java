package com.example.myapplication.domain.usecase.restaurant.menu.requireChoices;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;

import io.reactivex.rxjava3.core.Completable;

public class AddNewRequireChoiceVariantUseCase {
    public Completable invoke(String restaurantId, String categoryId, String dishId, String choiceId, String newVariant){
        return RestaurantMenuDI.requiredChoice.addNewRequiredChoiceVariant(restaurantId, categoryId, dishId, choiceId, newVariant);
    }
}
