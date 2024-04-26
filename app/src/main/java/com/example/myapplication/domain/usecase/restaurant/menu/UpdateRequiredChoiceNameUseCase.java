package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class UpdateRequiredChoiceNameUseCase {
    public Completable invoke(String restaurantId, String categoryId, String dishId, String choiceId, String name){
        return DI.restaurantRepository.updateRequiredChoiceName(restaurantId, categoryId, dishId, choiceId, name);
    }
}
