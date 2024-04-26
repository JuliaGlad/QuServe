package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class AddNewRequireChoiceVariantUseCase {
    public Completable invoke(String restaurantId, String categoryId, String dishId, String choiceId, String newVariant){
        return DI.restaurantRepository.addNewRequiredChoiceVariant(restaurantId, categoryId, dishId, choiceId, newVariant);
    }
}
