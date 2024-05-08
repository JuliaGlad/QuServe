package com.example.myapplication.domain.usecase.restaurant.menu.requireChoices;

import com.example.myapplication.di.restaurant.RestaurantMenuDI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteRequiredChoiceVariantUseCase {
    public Completable invoke(String restaurantId, String categoryId, String dishId, String choiceId, String variant){
        return RestaurantMenuDI.requiredChoice.deleteRequiredChoiceVariant(restaurantId, categoryId, dishId, choiceId, variant);
    }
}
