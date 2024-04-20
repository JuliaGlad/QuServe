package com.example.myapplication.domain.usecase.restaurant.menu;

import com.example.myapplication.di.DI;

import io.reactivex.rxjava3.core.Completable;

public class DeleteRequiredChoiceByIdUseCase {
    public Completable invoke(String restaurantId, String categoryId, String dishId, String choiceId){
        return DI.restaurantOwnerRepository.deleteRequiredChoiceById(restaurantId, categoryId, dishId, choiceId);
    }
}
