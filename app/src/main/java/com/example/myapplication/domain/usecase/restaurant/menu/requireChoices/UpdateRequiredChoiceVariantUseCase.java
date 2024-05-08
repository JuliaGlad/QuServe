package com.example.myapplication.domain.usecase.restaurant.menu.requireChoices;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateRequiredChoiceVariantUseCase {
    public Completable invoke(String restaurantId, String categoryId, String dishId, String choiceId, String previousVariant, String newVariant){
        return RestaurantMenuDI.requiredChoice.updateVariant(restaurantId, categoryId, dishId, choiceId, previousVariant, newVariant);
    }
}
