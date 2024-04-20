package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteRequiredChoiceVariantUseCase {
    public Completable invoke(String restaurantId, String categoryId, String dishId, String choiceId, String variant){
        return DI.restaurantOwnerRepository.deleteRequiredChoiceVariant(restaurantId, categoryId, dishId, choiceId, variant);
    }
}
