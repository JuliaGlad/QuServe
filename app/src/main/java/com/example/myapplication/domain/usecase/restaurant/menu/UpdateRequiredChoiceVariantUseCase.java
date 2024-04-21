package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateRequiredChoiceVariantUseCase {
    public Completable invoke(String restaurantId, String categoryId, String dishId, String choiceId, String previousVariant, String newVariant){
        return DI.restaurantOwnerRepository.updateVariant(restaurantId, categoryId, dishId, choiceId, previousVariant, newVariant);
    }
}